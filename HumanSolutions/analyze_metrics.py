"""
Java Code Metrics Analysis Script
==================================
Calculates CCN, Halstead Volume, SLOC, and Maintainability Index
for all 83 Java files across 16 subjects.

Metric Definitions (per Section 3.4.1):
  CCN     = decision_points + 1
            decision points: if, while, for, catch, &&, ||, ?
  V       = (N1 + N2) * log2(n1 + n2)
            operators = Modifier/Keyword/BasicType/Operator tokens
            operands  = Identifier/literal tokens
  SLOC    = non-blank lines (comment lines included)
  MI_raw  = 171 - 5.2*ln(V) - 0.23*CCN - 16.2*ln(SLOC)
  MI_norm = (MI_raw / 171) * 100

Statistics (Section 3.5):
  Mean, Std, 95% CI using Z = 1.96, ME = 1.96 * (s / sqrt(n))
"""

import os
import math
import csv
import javalang
from statistics import mean, stdev


# ---------------------------------------------------------------------------
# Helpers
# ---------------------------------------------------------------------------

def count_sloc(source: str) -> int:
    """Count non-blank lines (including comment-only lines)."""
    return sum(1 for line in source.split('\n') if line.strip())


def calculate_ccn(source: str) -> tuple[int, int]:
    """
    Returns (decision_points, CCN).
    Falls back to regex-based counting if the AST parse fails.
    """
    try:
        tree = javalang.parse.parse(source)
    except Exception:
        return _ccn_fallback(source)

    dp = 0
    for _, node in tree:
        if isinstance(node, (
            javalang.tree.IfStatement,
            javalang.tree.WhileStatement,
            javalang.tree.ForStatement,
            javalang.tree.EnhancedForControl,
            javalang.tree.CatchClause,
        )):
            dp += 1
        elif isinstance(node, javalang.tree.BinaryOperation):
            if node.operator in ('&&', '||'):
                dp += 1
        elif isinstance(node, javalang.tree.TernaryExpression):
            dp += 1
    return dp, dp + 1


def _ccn_fallback(source: str) -> tuple[int, int]:
    """Regex-based CCN for files that cannot be parsed by javalang."""
    import re
    # Remove string/char literals and comments to avoid false matches
    source = re.sub(r'"(?:[^"\\]|\\.)*"', '""', source)
    source = re.sub(r"'(?:[^'\\]|\\.)*'", "''", source)
    source = re.sub(r'//[^\n]*', '', source)
    source = re.sub(r'/\*.*?\*/', '', source, flags=re.DOTALL)

    dp = 0
    dp += len(re.findall(r'\bif\s*\(', source))
    dp += len(re.findall(r'\bwhile\s*\(', source))
    dp += len(re.findall(r'\bfor\s*\(', source))
    dp += len(re.findall(r'\bcatch\s*\(', source))
    dp += source.count('&&')
    dp += source.count('||')
    dp += source.count('?')
    return dp, dp + 1


def calculate_halstead(source: str) -> dict | None:
    """
    Returns dict with N1, N2, n1, n2, volume, or None on failure.

    Operator tokens : Modifier, Keyword, BasicType, Operator
    Operand  tokens : Identifier, Boolean, DecimalInteger, OctalInteger,
                      HexInteger, FloatingPoint, String, Character, Null
    """
    try:
        tokens = list(javalang.tokenizer.tokenize(source))
    except Exception:
        return None

    OPERATOR_TYPES = frozenset(('Modifier', 'Keyword', 'BasicType', 'Operator'))
    OPERAND_TYPES  = frozenset((
        'Identifier', 'Boolean', 'DecimalInteger', 'OctalInteger',
        'HexInteger', 'FloatingPoint', 'String', 'Character', 'Null',
    ))

    operators, operands = [], []
    for tok in tokens:
        tn = type(tok).__name__
        if tn in OPERATOR_TYPES:
            operators.append(tok.value)
        elif tn in OPERAND_TYPES:
            operands.append(tok.value)

    N1, N2 = len(operators), len(operands)
    n1, n2 = len(set(operators)), len(set(operands))

    if n1 + n2 < 2:
        return None

    volume = (N1 + N2) * math.log2(n1 + n2)
    return {'N1': N1, 'N2': N2, 'n1': n1, 'n2': n2, 'volume': volume}


def calculate_mi(volume: float, ccn: int, sloc: int) -> tuple[float, float] | tuple[None, None]:
    """Returns (MI_raw, MI_norm). Returns (None, None) if inputs are invalid."""
    if volume <= 0 or sloc <= 0:
        return None, None
    mi_raw  = 171 - 5.2 * math.log(volume) - 0.23 * ccn - 16.2 * math.log(sloc)
    mi_norm = (mi_raw / 171) * 100
    return round(mi_raw, 4), round(mi_norm, 4)


# ---------------------------------------------------------------------------
# File discovery
# ---------------------------------------------------------------------------

def find_java_files(root: str) -> list[str]:
    """Recursively find all .java files under root."""
    results = []
    for dirpath, _, filenames in os.walk(root):
        for fname in filenames:
            if fname.endswith('.java'):
                results.append(os.path.join(dirpath, fname))
    return sorted(results)


# ---------------------------------------------------------------------------
# Main analysis
# ---------------------------------------------------------------------------

def analyse_file(filepath: str) -> dict:
    with open(filepath, encoding='utf-8', errors='replace') as f:
        source = f.read()

    sloc = count_sloc(source)
    dp, ccn = calculate_ccn(source)
    hal = calculate_halstead(source)

    if hal is None:
        volume = None
        mi_raw = mi_norm = None
    else:
        volume = round(hal['volume'], 4)
        mi_raw, mi_norm = calculate_mi(hal['volume'], ccn, sloc)

    # Extract subject and task from path
    parts = filepath.replace('\\', '/').split('/')
    subject = next((p for p in parts if p.startswith('Subject')), 'Unknown')
    task    = next((p for p in parts if p.startswith('Task')),    'Unknown')

    return {
        'subject':   subject,
        'task':      task,
        'file':      os.path.basename(filepath),
        'filepath':  filepath,
        'sloc':      sloc,
        'dp':        dp,
        'ccn':       ccn,
        'n1':        hal['n1']  if hal else None,
        'n2':        hal['n2']  if hal else None,
        'N1':        hal['N1']  if hal else None,
        'N2':        hal['N2']  if hal else None,
        'halstead_v': volume,
        'mi_raw':    mi_raw,
        'mi_norm':   mi_norm,
    }


def descriptive_stats(values: list[float]) -> dict:
    n   = len(values)
    mu  = mean(values)
    s   = stdev(values) if n > 1 else 0.0
    me  = 1.96 * (s / math.sqrt(n))
    return {
        'n':    n,
        'mean': round(mu, 4),
        'std':  round(s,  4),
        'me':   round(me, 4),
        'ci_lo': round(mu - me, 4),
        'ci_hi': round(mu + me, 4),
    }


# ---------------------------------------------------------------------------
# Entry point
# ---------------------------------------------------------------------------

ROOT    = os.path.dirname(os.path.abspath(__file__))
OUT_CSV = os.path.join(ROOT, 'metrics_results.csv')
OUT_STATS_CSV = os.path.join(ROOT, 'metrics_statistics.csv')

print(f"Scanning Java files under: {ROOT}")
java_files = find_java_files(ROOT)
print(f"Found {len(java_files)} Java file(s).\n")

rows = []
for fp in java_files:
    row = analyse_file(fp)
    rows.append(row)
    status = "OK" if row['mi_norm'] is not None else "PARSE_ERROR"
    print(f"  [{status}] {row['subject']}/{row['task']}/{row['file']}"
          f"  SLOC={row['sloc']}  CCN={row['ccn']}"
          f"  V={row['halstead_v']}  MI_norm={row['mi_norm']}")

# ---------- write per-file CSV ----------
fieldnames = [
    'subject', 'task', 'file', 'filepath',
    'sloc', 'dp', 'ccn',
    'n1', 'n2', 'N1', 'N2', 'halstead_v',
    'mi_raw', 'mi_norm',
]
with open(OUT_CSV, 'w', newline='', encoding='utf-8') as f:
    writer = csv.DictWriter(f, fieldnames=fieldnames)
    writer.writeheader()
    writer.writerows(rows)
print(f"\nPer-file results written to: {OUT_CSV}")

# ---------- statistics ----------
valid = [r for r in rows if r['mi_norm'] is not None]
print(f"\n{'='*60}")
print(f"Valid files for statistics: {len(valid)} / {len(rows)}")

metrics = {
    'CCN':        [r['ccn']        for r in valid],
    'Halstead_V': [r['halstead_v'] for r in valid],
    'SLOC':       [r['sloc']       for r in valid],
    'MI_raw':     [r['mi_raw']     for r in valid],
    'MI_norm':    [r['mi_norm']    for r in valid],
}

stat_rows = []
print(f"\n{'Metric':<14} {'n':>4} {'Mean':>10} {'Std':>10} {'ME':>8} {'CI_lo':>10} {'CI_hi':>10}")
print('-' * 70)
for name, vals in metrics.items():
    s = descriptive_stats(vals)
    stat_rows.append({'metric': name, **s})
    print(f"{name:<14} {s['n']:>4} {s['mean']:>10.4f} {s['std']:>10.4f} "
          f"{s['me']:>8.4f} {s['ci_lo']:>10.4f} {s['ci_hi']:>10.4f}")

with open(OUT_STATS_CSV, 'w', newline='', encoding='utf-8') as f:
    writer = csv.DictWriter(f, fieldnames=['metric', 'n', 'mean', 'std', 'me', 'ci_lo', 'ci_hi'])
    writer.writeheader()
    writer.writerows(stat_rows)
print(f"\nStatistics written to: {OUT_STATS_CSV}")

# ---------- calibration check ----------
print(f"\n{'='*60}")
print("Calibration check (Subject4/Task1_PasswordValidator/PasswordValidator.java):")
ref = next(r for r in rows if 'Subject4' in r['filepath'] and 'PasswordValidator' in r['file'])
print(f"  SLOC       = {ref['sloc']}   (paper: 18)")
print(f"  CCN        = {ref['ccn']}    (paper: 5)")
print(f"  Halstead V = {ref['halstead_v']}  (paper: ~480)")
print(f"  MI_norm    = {ref['mi_norm']} (paper: 53.18)")
