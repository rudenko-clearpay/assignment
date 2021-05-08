function isNumber(value, msg = "Should be a number") {
    return isNaN(value) ? msg : "";
}

function isLessThan(valueA, valueB) {
    return valueA < valueB ? "Not enough balance" : "";
}


