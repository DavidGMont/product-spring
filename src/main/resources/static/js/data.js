/**
 * Formatea un valor como moneda
 * @param {number|string} value - Valor a formatear
 * @returns {string} - Valor formateado como moneda
 */
export function formatCurrency(value, options = {}) {
    const { locale = 'es-UY', currency = 'UYU', minDecimals = 2, maxDecimals = 2 } = options;
    
    return Intl.NumberFormat(locale, {
        style: 'currency',
        currency: currency,
        minimumFractionDigits: minDecimals,
        maximumFractionDigits: maxDecimals
    }).format(value);
}
