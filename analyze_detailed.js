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
    const lines = code.split('\n');
    let sloc = 0;
    for (let line of lines) {
        line = line.trim();
        if (line.length > 0 && !line.startsWith('//') && !line.startsWith('/*') && !line.startsWith('*')) {
            sloc++;
        }
    }
    if (sloc === 0) sloc = 1;

    let ccn = 1;
    const decisionRegex = /\b(if|while|for|catch|case)\b|&&|\|\||\?/g;
    let match;
    while ((match = decisionRegex.exec(code)) !== null) {
        ccn++;
    }

    const tokens = code.match(/\w+|[^\w\s]/g) || [];
    const keywordsAndOperators = new Set(['+', '-', '*', '/', '%', '=', '==', '!=', '>', '<', '>=', '<=', '&&', '||', '!', '&', '|', '^', '~', '<<', '>>', '>>>', '++', '--', '+=', '-=', '*=', '/=', '%=', '&=', '|=', '^=', '<<=', '>>=', '>>>=', '?', ':', '.', '::', 'if', 'else', 'for', 'while', 'do', 'switch', 'case', 'default', 'break', 'continue', 'return', 'try', 'catch', 'finally', 'throw', 'throws', 'class', 'interface', 'enum', 'extends', 'implements', 'public', 'protected', 'private', 'static', 'final', 'abstract', 'synchronized', 'volatile', 'transient', 'native', 'strictfp', 'new', 'this', 'super', 'instanceof', 'true', 'false', 'null', '{', '}', '(', ')', '[', ']', ';', ',']);
    
    let N1 = 0, N2 = 0;
    let n1Set = new Set(), n2Set = new Set();

    for (let token of tokens) {
        if (keywordsAndOperators.has(token)) {
            N1++; n1Set.add(token);
        } else {
            N2++; n2Set.add(token);
        }
    }

    const N = N1 + N2;
    const n = n1Set.size + n2Set.size;
    let V = n > 0 ? N * Math.log2(n) : 1;
    if (V === 0) V = 1;

    let MI = 171 - 5.2 * Math.log(V) - 0.23 * ccn - 16.2 * Math.log(sloc);
    let MI_norm = Math.max(0, (MI * 100) / 171);

    return { sloc, ccn, V, MI: MI_norm };
}

const allFiles = getFiles('C:/Users/asmar/supplementaryartifacts/HumanSolutions');
const results = [];

for (let file of allFiles) {
    const code = fs.readFileSync(file, 'utf8');
    const m = calculateMetrics(code);
    const relPath = path.relative('C:/Users/asmar/supplementaryartifacts/HumanSolutions', file);
    results.push({ path: relPath, ccn: m.ccn, mi: m.MI });
}

console.log(JSON.stringify(results, null, 2));
