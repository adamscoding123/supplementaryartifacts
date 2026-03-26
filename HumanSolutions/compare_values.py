"""
Compares the values from the provided table (image) against our script's output.
"""
import csv, os

# Values read from the images (Subject, Task, CCN, MI_norm)
image_values = [
    ("Subject1",  "Task1", 10,  46.47),
    ("Subject1",  "Task2",  4,  44.84),
    ("Subject1",  "Task3",  4,  49.35),
    ("Subject1",  "Task4",  3,  51.71),
    ("Subject1",  "Task5",  6,  46.52),
    ("Subject1",  "Task6", 19,  30.81),
    ("Subject2",  "Task1",  6,  50.17),
    ("Subject2",  "Task2",  4,  44.85),
    ("Subject2",  "Task3",  8,  45.28),
    ("Subject2",  "Task4",  8,  43.56),
    ("Subject2",  "Task5",  4,  51.19),
    ("Subject2",  "Task6", 17,  35.00),
    ("Subject3",  "Task1",  6,  49.61),
    ("Subject3",  "Task2",  3,  48.10),
    ("Subject3",  "Task3", 12,  46.18),
    ("Subject3",  "Task4",  2,  50.02),
    ("Subject3",  "Task5",  4,  49.69),
    ("Subject3",  "Task6", 15,  36.61),
    ("Subject4",  "Task1",  7,  49.21),
    ("Subject4",  "Task2",  4,  44.93),
    ("Subject4",  "Task3",  8,  46.54),
    ("Subject4",  "Task4",  5,  48.04),
    ("Subject4",  "Task5",  5,  48.62),
    ("Subject5",  "Task1",  9,  46.45),
    ("Subject5",  "Task2",  5,  44.47),
    ("Subject5",  "Task3",  8,  45.46),
    ("Subject5",  "Task4",  3,  51.72),
    ("Subject5",  "Task5",  7,  47.52),
    ("Subject6",  "Task1",  6,  50.38),
    ("Subject6",  "Task2",  3,  45.32),
    ("Subject6",  "Task3",  4,  50.60),
    ("Subject6",  "Task4",  2,  48.84),
    ("Subject6",  "Task5",  5,  48.54),
    ("Subject7",  "Task1", 13,  42.34),
    ("Subject7",  "Task2",  4,  44.72),
    ("Subject7",  "Task3",  6,  47.53),
    ("Subject7",  "Task4",  5,  49.98),
    ("Subject7",  "Task5",  5,  51.05),
    ("Subject8",  "Task1",  7,  50.93),
    ("Subject8",  "Task2",  4,  48.57),
    ("Subject8",  "Task3",  3,  52.29),
    ("Subject8",  "Task4",  2,  57.87),
    ("Subject8",  "Task5",  5,  54.33),
    ("Subject9",  "Task1", 12,  43.90),
]

# Load our script results
ROOT = os.path.dirname(os.path.abspath(__file__))
our_results = {}
with open(os.path.join(ROOT, 'metrics_detailed.csv'), encoding='utf-8') as f:
    for row in csv.DictReader(f):
        subj = row['Subject']
        task_full = row['Task']
        # Map e.g. "Task1_PasswordValidator" -> "Task1"
        task_key = task_full.split('_')[0]
        our_results[(subj, task_key)] = {
            'ccn':     int(row['CCN (L+1)']),
            'mi_norm': float(row['MI_norm (%)']),
        }

# Compare
print(f"{'Subj':<10} {'Task':<7} {'Img CCN':>8} {'Our CCN':>8} {'CCN':>6} | {'Img MI':>8} {'Our MI':>8} {'MI Diff':>8} {'MI':>6}")
print("-" * 85)

ccn_matches, mi_close = 0, 0
total = len(image_values)
rows_out = []

for subj, task, img_ccn, img_mi in image_values:
    key = (subj, task)
    if key not in our_results:
        print(f"{subj:<10} {task:<7} {'N/A':>8}")
        continue
    ours = our_results[key]
    our_ccn = ours['ccn']
    our_mi  = ours['mi_norm']

    ccn_ok  = img_ccn == our_ccn
    mi_diff = img_mi - our_mi
    mi_ok   = abs(mi_diff) <= 0.5

    if ccn_ok: ccn_matches += 1
    if mi_ok:  mi_close    += 1

    ccn_sym = "OK " if ccn_ok else f"ERR"
    mi_sym  = "OK " if mi_ok  else f"ERR"

    print(f"{subj:<10} {task:<7} {img_ccn:>8} {our_ccn:>8} {ccn_sym:>6} | {img_mi:>8.2f} {our_mi:>8.2f} {mi_diff:>+8.2f} {mi_sym:>6}")
    rows_out.append({
        'Subject': subj, 'Task': task,
        'Image_CCN': img_ccn, 'Script_CCN': our_ccn, 'CCN_Match': ccn_ok,
        'Image_MI': img_mi, 'Script_MI': round(our_mi,2), 'MI_Diff': round(mi_diff,2), 'MI_Within_0.5': mi_ok,
    })

print("-" * 85)
print(f"CCN exact matches : {ccn_matches}/{total}  ({100*ccn_matches/total:.0f}%)")
print(f"MI within ±0.5    : {mi_close}/{total}   ({100*mi_close/total:.0f}%)")

# CCN mismatch details
print("\nCCN MISMATCHES:")
for r in rows_out:
    if not r['CCN_Match']:
        diff = r['Image_CCN'] - r['Script_CCN']
        print(f"  {r['Subject']}/{r['Task']}: image={r['Image_CCN']}, script={r['Script_CCN']}, diff={diff:+d}")

# MI diff statistics
diffs = [r['MI_Diff'] for r in rows_out]
avg_diff = sum(diffs)/len(diffs)
print(f"\nMI differences (image - script):")
print(f"  Mean diff : {avg_diff:+.4f}")
print(f"  Min diff  : {min(diffs):+.4f}")
print(f"  Max diff  : {max(diffs):+.4f}")

# Write comparison CSV
with open(os.path.join(ROOT, 'comparison_image_vs_script.csv'), 'w', newline='', encoding='utf-8') as f:
    writer = csv.DictWriter(f, fieldnames=list(rows_out[0].keys()))
    writer.writeheader()
    writer.writerows(rows_out)
print(f"\nComparison written to: comparison_image_vs_script.csv")
