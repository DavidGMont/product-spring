/**
 * Módulo principal para la gestión de administración de productos
 */
import {config} from './config.js';
import {formatCurrency} from './data.js';
import {closeModal, toggleModal} from './modal.js';
import {
    isFormValid,
    validateInputFile,
    validateInputLength,
    validateInputNumber,
    validateInputSelect
} from './validation.js';
import {
    createBrand,
    createProduct,
    deleteProduct,
    getAllBrands,
    getAllCategories,
    getAllProducts as fetchProducts,
    getProductById,
    updateProduct,
    uploadFile
} from './api-service.js';

// Inicialización y configuración de event listeners

/**
 * Inicializa la aplicación
 */
function init() {
    // Configurar estado inicial - el checkbox available está checked por defecto
    formState.available = elements.availableInput.checked;
    formState.isValid.available = true;

    // Configurar event listeners
    setupEventListeners();

    // Cargar marcas y categorías
    loadBrandsAndCategories().then(() => {
        // Cargar productos después de cargar marcas y categorías
        loadAndDisplayProducts();
    });
}

/**
 * Configura los event listeners para los elementos del formulario
 */
function setupEventListeners() {
    // Botones del modal
    elements.registerButton.addEventListener('click', (e) => {
        resetFormState();
        toggleModal(e);
    });

    elements.cancelButton.addEventListener('click', toggleModal);

    // Formulario
    elements.form.addEventListener('submit', handleSubmitProduct);

    // Campos de texto
    elements.nameInput.addEventListener('input', (e) => {
        formState.name = e.target.value;
        formState.isValid.name = validateInputLength(e.target);
    });

    elements.descriptionInput.addEventListener('input', (e) => {
        formState.description = e.target.value;
        formState.isValid.description = validateInputLength(
            e.target,
            config.validation.description.minLength,
            config.validation.description.maxLength
        );
    });

    // Campos numéricos
    elements.priceInput.addEventListener('input', (e) => {
        formState.price = e.target.value;
        formState.isValid.price = validateInputNumber(e.target);
    });

    // Checkbox
    elements.availableInput.addEventListener('change', (e) => {
        formState.available = e.target.checked;
        // El checkbox siempre es válido, independientemente de si está checked o no
        formState.isValid.available = true;
    });

    // Selects
    elements.selectBrandInput.addEventListener('change', (e) => {
        const value = e.target.value;
        formState.brandId = value;

        if (value !== '0') {
            const brand = loadedBrands.find(b => b.id.toString() === value);
            formState.brandName = brand ? brand.name : '';
            elements.newBrandLabel.setAttribute('hidden', '');
            elements.newBrandInput.setAttribute('hidden', '');
            formState.isValid.brand = true;
        } else {
            // "Otra..." seleccionada
            elements.selectBrandInput.setAttribute('disabled', 'true');
            elements.newBrandLabel.removeAttribute('hidden');
            elements.newBrandInput.removeAttribute('hidden');
            formState.isValid.brand = false;
        }
    });

    elements.newBrandInput.addEventListener('input', (e) => {
        formState.brandName = e.target.value;
        formState.isValid.brand = validateInputLength(e.target);
    });

    elements.categoryInput.addEventListener('change', (e) => {
        formState.categoryId = e.target.value;
        formState.isValid.categoryId = validateInputSelect(e.target);
    });

    // Archivo
    elements.thumbnailInput.addEventListener('change', async (e) => {
        const isValid = await validateInputFile(e.target);
        formState.isValid.thumbnail = isValid;

        if (isValid && e.target.files[0]) {
            await handleUploadThumbnail(e.target.files[0]);
        }
    });
}

// Referencias a elementos del DOM
const elements = {
    tableBody: document.getElementById('products-table-body'),
    form: document.getElementById('product-form'),
    formTitle: document.getElementById('product-form-title'),
    formSubtitle: document.getElementById('product-form-subtitle'),
    idInput: document.getElementById('product-id'),
    nameInput: document.getElementById('product-name'),
    descriptionInput: document.getElementById('product-description'),
    selectBrandInput: document.getElementById('product-select-brand'),
    newBrandLabel: document.querySelector('label[for="product-new-brand"]'),
    newBrandInput: document.getElementById('product-new-brand'),
    priceInput: document.getElementById('product-price'),
    availableInput: document.getElementById('product-available'),
    thumbnailInput: document.getElementById('product-thumbnail'),
    categoryInput: document.getElementById('product-category'),
    submitButton: document.querySelector('button[type="submit"]'),
    registerButton: document.getElementById('register-button'),
    cancelButton: document.getElementById('product-form-cancel'),
    progressBar: document.querySelector('progress'),
    modal: document.getElementById('register-dialog')
};

// Estado del formulario
const formState = {
    name: undefined,
    description: undefined,
    brandId: undefined,
    brandName: undefined,
    price: undefined,
    available: true,
    thumbnail: undefined,
    categoryId: undefined,
    isValid: {
        name: false,
        description: false,
        brand: false,
        price: false,
        available: true,
        thumbnail: false,
        categoryId: false
    }
};

// Datos cargados desde la API
let loadedBrands = [];
let loadedCategories = [];

// Inicializar cuando el DOM esté cargado
document.addEventListener('DOMContentLoaded', init);

/**
 * Renderiza la tabla de productos
 * @param {Array} products - Lista de productos
 */
function renderProductsTable(products) {
    elements.tableBody.innerHTML = '';

    products.forEach(product => {
        const tr = document.createElement('tr');

        tr.innerHTML = `
            <td class="center">${product.id}</td>
            <td class="center">
                <img
                    src="${config.server.url + product.thumbnail}"
                    alt="${product.name}"
                    width="100"
                    height="100" />
            </td>
            <td>${product.name}</td>
            <td>${product.description}</td>
            <td class="center">${product.brand.name}</td>
            <td class="center">${formatCurrency(product.price.toFixed(2))}</td>
            <td class="center">
                <span data-tooltip="${product.available ? 'Disponible' : 'No disponible'}">
                    <img
                        src="${product.available ? 'img/circle-check.svg' : 'img/circle-xmark.svg'}"
                        alt="${product.available ? 'Disponible' : 'No disponible'}"
                        width="30"
                        height="30" />
                </span>
            </td>
            <td class="center">
                <span data-tooltip="${product.category.name}">
                    <img
                        src="${product.category.thumbnail}"
                        alt="${product.category.name}"
                        width="30"
                        height="30" />
                </span>
            </td>
            <td class="actions center">
                <button
                    type="button"
                    class="edit secondary"
                    data-placement="left"
                    data-tooltip="Editar"
                    data-id="${product.id}">
                    <img
                        src="img/pen-to-square-solid.svg"
                        alt="Editar"
                        width="20"
                        height="20" />
                </button>
                <button
                    type="button"
                    class="delete"
                    data-placement="left"
                    data-tooltip="Eliminar"
                    data-id="${product.id}">
                    <img
                        src="img/trash-solid.svg"
                        alt="Eliminar"
                        width="20"
                        height="20" />
                </button>
            </td>
        `;

        elements.tableBody.appendChild(tr);

        // Agregar event listeners a los botones
        tr.querySelector('.edit').addEventListener('click', () =>
            handleEditProduct(product.id)
        );

        tr.querySelector('.delete').addEventListener('click', () =>
            handleDeleteProduct(product.id)
        );
    });
}

/**
 * Carga los productos y los muestra en la tabla
 */
async function loadAndDisplayProducts() {
    try {
        elements.progressBar.removeAttribute('hidden');
        const products = await fetchProducts();
        renderProductsTable(products);
    } catch (error) {
        alert('Error al cargar los productos. Por favor, intenta de nuevo.');
    } finally {
        elements.progressBar.setAttribute('hidden', '');
    }
}

/**
 * Maneja la eliminación de un producto
 * @param {number} id - ID del producto a eliminar
 */
async function handleDeleteProduct(id) {
    if (!confirm('¿Estás seguro de que deseas eliminar este producto? Esta acción no se puede deshacer.')) {
        return;
    }

    try {
        elements.progressBar.removeAttribute('hidden');
        await deleteProduct(id);
        await loadAndDisplayProducts();
    } catch (error) {
        alert(`Error al eliminar el producto: ${error.message}`);
    } finally {
        elements.progressBar.setAttribute('hidden', '');
    }
}

/**
 * Maneja la subida de una imagen
 * @param {File} file - Archivo a subir
 */
async function handleUploadThumbnail(file) {
    try {
        formState.thumbnail = await uploadFile(file);
    } catch (error) {
        alert(`Error al subir la imagen: ${error.message}`);
        formState.thumbnail = undefined;
        formState.isValid.thumbnail = false;
    }
}

/**
 * Carga las marcas y categorías desde la API
 */
async function loadBrandsAndCategories() {
    try {
        elements.progressBar.removeAttribute('hidden');

        // Cargar marcas
        const brands = await getAllBrands();
        loadedBrands = brands;

        // Limpiar y poblar el select de marcas
        const selectBrand = elements.selectBrandInput;
        // Mantener solo la opción por defecto y "Otra..."
        while (selectBrand.options.length > 2) {
            selectBrand.remove(1);
        }

        // Agregar las marcas
        brands.forEach(brand => {
            const option = document.createElement('option');
            option.value = brand.id;
            option.textContent = brand.name;
            // Insertar antes de la opción "Otra..."
            selectBrand.insertBefore(option, selectBrand.options[selectBrand.options.length - 1]);
        });

        // Cargar categorías
        const categories = await getAllCategories();
        loadedCategories = categories;

        // Limpiar y poblar el select de categorías
        const selectCategory = elements.categoryInput;
        // Mantener solo la opción por defecto
        while (selectCategory.options.length > 1) {
            selectCategory.remove(1);
        }

        // Agregar las categorías
        categories.forEach(category => {
            const option = document.createElement('option');
            option.value = category.id;
            option.textContent = category.name;
            selectCategory.appendChild(option);
        });
    } catch (error) {
        alert(`Error al cargar marcas y categorías: ${error.message}`);
    } finally {
        elements.progressBar.setAttribute('hidden', '');
    }
}

/**
 * Resetea el estado del formulario
 */
function resetFormState() {
    // Resetear estado
    Object.keys(formState).forEach(key => {
        if (key !== 'isValid' && key !== 'available') {
            formState[key] = undefined;
        }
    });

    // Resetear validaciones mantieniendo available en true
    Object.keys(formState.isValid).forEach(key => {
        formState.isValid[key] = key === 'available';
    });

    // Resetear formulario (sin perder el estado del checkbox)
    const wasChecked = elements.availableInput.checked;
    elements.form.reset();
    elements.availableInput.checked = wasChecked;
    formState.available = wasChecked;

    // Limpiar ID para asegurar que se trata como nuevo producto
    elements.idInput.value = '';

    // Ocultar mensajes de error
    const invalidHelpers = document.querySelectorAll('[id$="-invalid-helper"]');
    invalidHelpers.forEach(helper => {
        helper.setAttribute('hidden', '');
        const inputId = helper.id.replace('-invalid-helper', '');
        const input = document.getElementById(inputId);
        if (input) {
            input.removeAttribute('aria-invalid');
        }
    });

    // Configurar UI para agregar nuevo producto
    elements.formTitle.textContent = '¡Agrega un instrumento!';
    elements.formSubtitle.textContent = 'Ingresa todos los datos ¡y ya está!';
    elements.submitButton.textContent = '¡Guardar en el catálogo!';
    elements.cancelButton.textContent = '¡Ya no quiero agregar un instrumento!';

    // Ocultar campo de nueva marca
    elements.newBrandLabel.setAttribute('hidden', '');
    elements.newBrandInput.setAttribute('hidden', '');
    elements.selectBrandInput.removeAttribute('disabled');
}

/**
 * Maneja el envío del formulario
 * @param {Event} event - Evento de envío
 */
async function handleSubmitProduct(event) {
    event.preventDefault();

    if (!isFormValid(formState.isValid)) {
        alert('Por favor, completa todos los campos correctamente.');
        return;
    }

    const productData = {
        name: formState.name,
        description: formState.description,
        price: parseFloat(formState.price),
        available: elements.availableInput.checked,
        thumbnail: formState.thumbnail,
        brand: {
            id: parseInt(formState.brandId)
        },
        category: {
            id: parseInt(formState.categoryId)
        }
    };

    // Si es una marca nueva, intentar crearla primero
    if (formState.brandId === '0' && formState.brandName) {
        try {
            const newBrand = await createBrand({
                name: formState.brandName,
                available: true
            });

            productData.brand.id = newBrand.id;
        } catch (error) {
            alert(`Error al crear la nueva marca: ${error.message}`);
            return;
        }
    }

    // UI feedback - botón en estado de carga
    elements.submitButton.setAttribute('aria-busy', 'true');
    elements.submitButton.textContent = 'Guardando...';

    try {
        const id = parseInt(elements.idInput.value);
        let result;

        if (id) {
            // Actualizar producto existente
            result = await updateProduct(id, productData);
        } else {
            // Crear nuevo producto
            result = await createProduct(productData);
        }

        // Éxito
        elements.submitButton.textContent = '¡Guardado con éxito!';
        setTimeout(() => {
            closeModal(elements.modal, resetFormState);
            loadAndDisplayProducts();
        }, 1000);
    } catch (error) {
        // Error
        elements.submitButton.textContent = 'Error al guardar';
        alert(`Error: ${error.message || 'No se pudo guardar el producto'}`);
    } finally {
        // Restablecer estado del botón
        setTimeout(() => {
            elements.submitButton.removeAttribute('aria-busy');
        }, 1000);
    }
}

/**
 * Maneja la edición de un producto
 * @param {number} id - ID del producto a editar
 */
async function handleEditProduct(id) {
    try {
        elements.progressBar.removeAttribute('hidden');

        // Obtener datos del producto
        const product = await getProductById(id);

        // Configurar UI para edición
        elements.formTitle.textContent = '¡Actualiza ese producto viejo!';
        elements.formSubtitle.textContent = 'Porque es necesario estar al día.';
        elements.submitButton.textContent = '¡Actualizar ya!';
        elements.cancelButton.textContent = 'Me arrepentí y no quiero continuar.';

        // Llenar el formulario con los datos del producto
        elements.idInput.value = product.id;
        elements.nameInput.value = product.name;
        elements.descriptionInput.value = product.description;
        elements.priceInput.value = product.price;
        elements.availableInput.checked = product.available;
        elements.categoryInput.value = product.category.id;

        // Manejar la marca (existente o nueva)
        const brandOption = loadedBrands.find(b => b.id === product.brand.id);
        if (brandOption) {
            elements.selectBrandInput.value = brandOption.id;
            elements.newBrandLabel.setAttribute('hidden', '');
            elements.newBrandInput.setAttribute('hidden', '');
        } else {
            elements.selectBrandInput.value = 0;
            elements.newBrandLabel.removeAttribute('hidden');
            elements.newBrandInput.removeAttribute('hidden');
            elements.newBrandInput.value = product.brand.name;
        }

        // Actualizar el estado del formulario
        formState.name = product.name;
        formState.description = product.description;
        formState.brandId = product.brand.id.toString();
        formState.brandName = product.brand.name;
        formState.price = product.price;
        formState.available = product.available;
        formState.thumbnail = product.thumbnail;
        formState.categoryId = product.category.id.toString();

        // Marcar todos los campos como válidos
        Object.keys(formState.isValid).forEach(key => {
            formState.isValid[key] = true;
        });

        // Abrir el modal
        toggleModal({
            preventDefault: () => {
            },
            currentTarget: {dataset: {target: 'register-dialog'}}
        });
    } catch (error) {
        alert(`Error al cargar el producto: ${error.message}`);
    } finally {
        elements.progressBar.setAttribute('hidden', '');
    }
}