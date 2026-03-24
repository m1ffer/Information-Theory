let POLYNOMIAL = "x^31 + x^3 + 1";

const ENABLE_POLY_EDIT = true;
const seedInput = document.getElementById("seedBits");

// 🔥 хранение результата
let lastResultBytes = null;

// степень
function getMaxPower(poly) {
    const matches = poly.match(/x\^(\d+)/g);
    if (!matches) return 1;
    return Math.max(...matches.map(m => parseInt(m.replace("x^", ""))));
}

// обновление счетчика
function updateBitInfo() {
    const bits = seedInput.value;
    const maxPower = getMaxPower(POLYNOMIAL);

    const info = document.getElementById("bitInfo");

    if (bits.length === maxPower) {
        info.textContent = `✔ ${bits.length} / ${maxPower}`;
        info.style.color = "green";
    } else {
        info.textContent = `✖ ${bits.length} / ${maxPower}`;
        info.style.color = "red";
    }
}

function editPolynomial() {

    const newPoly = prompt("Введите полином (например: x^5 + x^2 + 1)");

    if (!newPoly) return;

    // простая проверка
    if (!/x\^\d+/.test(newPoly)) {
        alert("Некорректный полином");
        return;
    }

    POLYNOMIAL = newPoly;

    // обновляем UI
    document.getElementById("polyDisplay").textContent =
        "Полином: " + POLYNOMIAL;

    // 🔥 пересчёт степени
    const maxPower = getMaxPower(POLYNOMIAL);

    // 🔥 обрезаем seed если длиннее
    let current = seedInput.value;

    if (current.length > maxPower) {
        current = current.substring(0, maxPower);
    }

    seedInput.value = current;

    updateBitInfo();
}

// фильтрация ввода
seedInput.addEventListener("input", () => {
    const maxPower = getMaxPower(POLYNOMIAL);

    let value = seedInput.value.replace(/[^01]/g, "");

    if (value.length > maxPower) {
        value = value.substring(0, maxPower);
    }

    seedInput.value = value;

    updateBitInfo();
});

// seed
function getSeedFromBits() {
    const bits = seedInput.value;
    const maxPower = getMaxPower(POLYNOMIAL);

    if (bits.length !== maxPower) {
        alert(`Seed должен быть длиной ${maxPower} бит`);
        return null;
    }

    return parseInt(bits, 2);
}

// 🔥 рендер байтов
function renderBytes(containerId, bytes) {
    const container = document.getElementById(containerId);
    container.innerHTML = "";

    for (let i = 0; i < bytes.length; i++) {
        const b = bytes[i] & 0xFF;
        const bits = b.toString(2).padStart(8, "0");

        const span = document.createElement("span");
        span.className = "byte";
        span.textContent = bits;

        container.appendChild(span);
    }
}

// 🔥 основная логика
async function process() {

    const fileInput = document.getElementById("fileInput");

    if (fileInput.files.length === 0) {
        alert("Выбери файл");
        return;
    }

    const seed = getSeedFromBits();
    if (seed === null) return;

    const file = fileInput.files[0];

    // ---------- вход
    const buffer = await file.arrayBuffer();
    const inputBytes = new Uint8Array(buffer);

    renderBytes("inputBits", inputBytes);

    // ---------- ключ
    const keyForm = new FormData();
    keyForm.append("seed", seed);
    keyForm.append("polynomial", POLYNOMIAL);
    keyForm.append("length", inputBytes.length);

    const keyResponse = await fetch("http://localhost:8080/crypto/key", {
        method: "POST",
        body: keyForm
    });

    const keyBytes = new Uint8Array(await keyResponse.arrayBuffer());

    renderBytes("keyOutput", keyBytes);

    // ---------- шифр
    const formData = new FormData();
    formData.append("file", file);
    formData.append("seed", seed);
    formData.append("polynomial", POLYNOMIAL);

    const response = await fetch("http://localhost:8080/crypto/encrypt", {
        method: "POST",
        body: formData
    });

    const outBytes = new Uint8Array(await response.arrayBuffer());

    renderBytes("output", outBytes);

    // 🔥 сохраняем результат
    lastResultBytes = outBytes;

    // 🔥 показываем кнопку скачивания
    document.getElementById("downloadBtn").style.display = "inline-block";
}

async function downloadResult() {

    if (!lastResultBytes) {
        alert("Нет данных для скачивания");
        return;
    }

    try {
        // 🔥 открывается нормальный диалог "Сохранить как"
        const handle = await window.showSaveFilePicker({
            suggestedName: "result.bin",
            types: [{
                description: "Binary file",
                accept: { "application/octet-stream": [".bin"] }
            }]
        });

        const writable = await handle.createWritable();

        await writable.write(lastResultBytes);
        await writable.close();

    } catch (err) {
        console.log("Пользователь отменил сохранение");
    }
}

// init
window.onload = () => {
    updateBitInfo();

    if (!ENABLE_POLY_EDIT) {
        document.getElementById("editPolyBtn").style.display = "none";
    }
};