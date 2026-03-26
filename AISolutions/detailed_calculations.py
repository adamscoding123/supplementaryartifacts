"""
AI Solutions (Gemini) – Detailed step-by-step calculation table.
Outputs metrics_detailed.csv with every intermediate value shown.
"""
import os, math, csv, javalang

OPERATOR_TYPES = frozenset(('Modifier', 'Keyword', 'BasicType', 'Operator'))
OPERAND_TYPES  = frozenset(('Identifier','Boolean','DecimalInteger','OctalInteger',
                             'HexInteger','FloatingPoint','String','Character','Null'))

def count_sloc(src):
    return sum(1 for line in src.split('\n') if line.strip())

def count_dp_breakdown(src):
    bd = {'if': 0, 'while': 0, 'for': 0, 'catch': 0, '&&': 0, '||': 0, '?': 0}
    try:
        tree = javalang.parse.parse(src)
        for _, node in tree:
            if isinstance(node, javalang.tree.IfStatement):
                bd['if'] += 1
            elif isinstance(node, javalang.tree.WhileStatement):
                bd['while'] += 1
            elif isinstance(node, (javalang.tree.ForStatement,
                                   javalang.tree.EnhancedForControl)):
                bd['for'] += 1
            elif isinstance(node, javalang.tree.CatchClause):
                bd['catch'] += 1
            elif isinstance(node, javalang.tree.BinaryOperation):
                if node.operator == '&&':
                    bd['&&'] += 1
                elif node.operator == '||':
                    bd['||'] += 1
            elif isinstance(node, javalang.tree.TernaryExpression):
                bd['?'] += 1
    except Exception:
        pass
    return bd

def halstead(src):
    try:
        tokens = list(javalang.tokenizer.tokenize(src))
    except Exception:
        return None
    ops, opds = [], []
    for t in tokens:
        tn = type(t).__name__
        if tn in OPERATOR_TYPES:
            ops.append(t.value)
        elif tn in OPERAND_TYPES:
            opds.append(t.value)
    N1, N2 = len(ops), len(opds)
    n1, n2 = len(set(ops)), len(set(opds))
    if n1 + n2 < 2:
        return None
    V = (N1 + N2) * math.log2(n1 + n2)
    return N1, N2, n1, n2, V


ROOT = os.path.dirname(os.path.abspath(__file__))

java_files = sorted([
    os.path.join(dirpath, fname)
    for dirpath, _, filenames in os.walk(ROOT)
    for fname in filenames
    if fname.endswith('.java')
])

rows = []
for fp in java_files:
    src = open(fp, encoding='utf-8', errors='replace').read()

    norm_fp = fp.replace('\\', '/')
    path_parts = norm_fp.split('/')
    run   = next((p for p in path_parts if p.startswith('GeminiRun')), '')
    task  = os.path.splitext(os.path.basename(fp))[0]
    fname = os.path.basename(fp)

    sloc = count_sloc(src)
    bd   = count_dp_breakdown(src)
    dp   = sum(bd.values())
    ccn  = dp + 1

    hal_result = halstead(src)
    if hal_result is None:
        print(f"  [SKIP] {run}/{fname} — tokenizer failed")
        continue
    N1, N2, n1, n2, V = hal_result
    ln_V  = math.log(V)
    ln_S  = math.log(sloc)
    t1    = 5.2  * ln_V
    t2    = 0.23 * ccn
    t3    = 16.2 * ln_S
    mi_raw  = 171 - t1 - t2 - t3
    mi_norm = (mi_raw / 171) * 100

    rows.append({
        'Run':             run,
        'Task':            task,
        'File':            fname,
        # SLOC
        'SLOC':            sloc,
        # CCN breakdown
        'DP_if':           bd['if'],
        'DP_while':        bd['while'],
        'DP_for':          bd['for'],
        'DP_catch':        bd['catch'],
        'DP_&&':           bd['&&'],
        'DP_||':           bd['||'],
        'DP_?':            bd['?'],
        'Total_DP (L)':    dp,
        'CCN (L+1)':       ccn,
        # Halstead raw counts
        'n1 unique ops':   n1,
        'n2 unique opds':  n2,
        'N1 total ops':    N1,
        'N2 total opds':   N2,
        'N (N1+N2)':       N1 + N2,
        'n (n1+n2)':       n1 + n2,
        'log2(n)':         round(math.log2(n1 + n2), 4),
        # Halstead Volume
        'V = N*log2(n)':   round(V, 4),
        # MI intermediate
        'ln(V)':           round(ln_V, 4),
        'ln(SLOC)':        round(ln_S, 4),
        '5.2*ln(V)':       round(t1, 4),
        '0.23*CCN':        round(t2, 4),
        '16.2*ln(SLOC)':   round(t3, 4),
        # MI results
        'MI_raw':          round(mi_raw,  4),
        'MI_norm (%)':     round(mi_norm, 4),
    })

fieldnames = list(rows[0].keys())
out_path = os.path.join(ROOT, 'metrics_detailed.csv')
with open(out_path, 'w', newline='', encoding='utf-8') as f:
    writer = csv.DictWriter(f, fieldnames=fieldnames)
    writer.writeheader()
    writer.writerows(rows)

print(f"Wrote {len(rows)} rows to: {out_path}")
print()

# Pretty-print to console
hdr = (
    f"{'#':>3} {'Run':<12} {'Task':<28} "
    f"{'SLOC':>5} {'if':>3} {'wh':>3} {'fo':>3} {'ca':>3} {'&&':>3} {'||':>3} {'?':>3} "
    f"{'L':>4} {'CCN':>4} "
    f"{'n1':>4} {'n2':>4} {'N1':>5} {'N2':>5} {'N':>5} {'n':>4} "
    f"{'log2n':>7} {'V':>9} "
    f"{'ln(V)':>7} {'ln(S)':>7} "
    f"{'5.2lnV':>8} {'0.23C':>7} {'16.2lnS':>8} "
    f"{'MI_raw':>8} {'MI_norm':>8}"
)
print(hdr)
print('-' * len(hdr))
for i, r in enumerate(rows, 1):
    print(
        f"{i:>3} {r['Run']:<12} {r['Task']:<28} "
        f"{r['SLOC']:>5} "
        f"{r['DP_if']:>3} {r['DP_while']:>3} {r['DP_for']:>3} "
        f"{r['DP_catch']:>3} {r['DP_&&']:>3} {r['DP_||']:>3} {r['DP_?']:>3} "
        f"{r['Total_DP (L)']:>4} {r['CCN (L+1)']:>4} "
        f"{r['n1 unique ops']:>4} {r['n2 unique opds']:>4} "
        f"{r['N1 total ops']:>5} {r['N2 total opds']:>5} "
        f"{r['N (N1+N2)']:>5} {r['n (n1+n2)']:>4} "
        f"{r['log2(n)']:>7} {r['V = N*log2(n)']:>9.2f} "
        f"{r['ln(V)']:>7} {r['ln(SLOC)']:>7} "
        f"{r['5.2*ln(V)']:>8.4f} {r['0.23*CCN']:>7.4f} {r['16.2*ln(SLOC)']:>8.4f} "
        f"{r['MI_raw']:>8.4f} {r['MI_norm (%)']:>8.4f}"
    )
