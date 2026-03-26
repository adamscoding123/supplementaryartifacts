const fs = require('fs');
const path = require('path');

function getFiles(dir, files_) {
    files_ = files_ || [];
    let files = fs.readdirSync(dir);
    for (let i in files) {
        let name = dir + '/' + files[i];
        if (fs.statSync(name).isDirectory()) {
            getFiles(name, files_);
        } else if (name.endsWith('.java')) {
            files_.push(name);
        }
    }
    return files_;
}

function calculateMetrics(code) {
    // 1. SLOC (Source Lines of Code)
    const lines = code.split('\n');
    let sloc = 0;
    for (let line of lines) {
        line = line.trim();
        if (line.length > 0 && !line.startsWith('//') && !line.startsWith('/*') && !line.startsWith('*')) {
            sloc++;
        }
    }
    if (sloc === 0) sloc = 1;

    // 2. CCN (Cyclomatic Complexity Number)
    // Count decision points
    let ccn = 1;
    const decisionRegex = /\b(if|while|for|catch|case)\b|&&|\|\||\?/g;
    let match;
    while ((match = decisionRegex.exec(code)) !== null) {
        ccn++;
    }

    // 3. Halstead Volume (V)
    const tokens = code.match(/\w+|[^\w\s]/g) || [];
    const keywordsAndOperators = new Set(['+', '-', '*', '/', '%', '=', '==', '!=', '>', '<', '>=', '<=', '&&', '||', '!', '&', '|', '^', '~', '<<', '>>', '>>>', '++', '--', '+=', '-=', '*=', '/=', '%=', '&=', '|=', '^=', '<<=', '>>=', '>>>=', '?', ':', '.', '::', 'if', 'else', 'for', 'while', 'do', 'switch', 'case', 'default', 'break', 'continue', 'return', 'try', 'catch', 'finally', 'throw', 'throws', 'class', 'interface', 'enum', 'extends', 'implements', 'public', 'protected', 'private', 'static', 'final', 'abstract', 'synchronized', 'volatile', 'transient', 'native', 'strictfp', 'new', 'this', 'super', 'instanceof', 'true', 'false', 'null', '{', '}', '(', ')', '[', ']', ';', ',']);
    
    let N1 = 0; // Total operators
    let N2 = 0; // Total operands
    let n1Set = new Set(); // Unique operators
    let n2Set = new Set(); // Unique operands

    for (let token of tokens) {
        if (keywordsAndOperators.has(token)) {
            N1++;
            n1Set.add(token);
        } else {
            N2++;
            n2Set.add(token);
        }
    }

    const N = N1 + N2;
    const n = n1Set.size + n2Set.size;
    let V = 0;
    if (n > 0) {
        V = N * Math.log2(n);
    }
    if (V === 0) V = 1;

    // 4. Maintainability Index (MI)
    let MI = 171 - 5.2 * Math.log(V) - 0.23 * ccn - 16.2 * Math.log(sloc);
    let MI_norm = Math.max(0, (MI * 100) / 171);

    return { sloc, ccn, V, MI: MI_norm };
}

function calculateConfidenceInterval(data) {
    if (data.length === 0) return { mean: 0, ci: 0 };
    const n = data.length;
    const mean = data.reduce((a, b) => a + b, 0) / n;
    const variance = data.reduce((a, b) => a + Math.pow(b - mean, 2), 0) / (n - 1 || 1);
    const sd = Math.sqrt(variance);
    // 95% CI Z-score is 1.96
    const ci = 1.96 * (sd / Math.sqrt(n));
    return { mean, sd, ci, lower: mean - ci, upper: mean + ci };
}

const allFiles = getFiles('C:/Users/asmar/supplementaryartifacts/HumanSolutions');

let ccnList = [];
let miList = [];

console.log("Analyzing " + allFiles.length + " files...\n");

for (let file of allFiles) {
    const code = fs.readFileSync(file, 'utf8');
    const metrics = calculateMetrics(code);
    ccnList.push(metrics.ccn);
    miList.push(metrics.MI);
}

const ccnStats = calculateConfidenceInterval(ccnList);
const miStats = calculateConfidenceInterval(miList);

console.log("--- Overall Metrics (Across All Subjects and Tasks) ---");
console.log(`Total Files Analyzed: ${allFiles.length}`);
console.log(`\nCyclomatic Complexity Number (CCN):`);
console.log(`  Mean: ${ccnStats.mean.toFixed(2)}`);
console.log(`  Std Dev: ${ccnStats.sd.toFixed(2)}`);
console.log(`  95% Confidence Interval: [${ccnStats.lower.toFixed(2)}, ${ccnStats.upper.toFixed(2)}]`);
console.log(`  Margin of Error: ±${ccnStats.ci.toFixed(2)}`);

console.log(`\nMaintainability Index (MI - Normalized 0-100):`);
console.log(`  Mean: ${miStats.mean.toFixed(2)}`);
console.log(`  Std Dev: ${miStats.sd.toFixed(2)}`);
console.log(`  95% Confidence Interval: [${miStats.lower.toFixed(2)}, ${miStats.upper.toFixed(2)}]`);
console.log(`  Margin of Error: ±${miStats.ci.toFixed(2)}`);
