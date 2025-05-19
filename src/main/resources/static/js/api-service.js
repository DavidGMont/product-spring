/**
 * Servicio API para la gestión de productos
 */
import { config } from './config.js';

const { server, api } = config;

/**
 * Obtiene todos los productos
 * @returns {Promise<Array>} - Promesa que resuelve a un array de productos
 */
export async function getAllProducts() {
    try {
        const response = await fetch(`${server.apiUrl}${api.products}`);
        if (!response.ok) {
            throw new Error(`Error fetching products: ${response.statusText}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching products:', error);
        throw error;
    }
}

/**
 * Obtiene un producto por su ID
 * @param {number} id - ID del producto
 * @returns {Promise<Object>} - Promesa que resuelve a un objeto producto
 */
export async function getProductById(id) {
    try {
        const response = await fetch(`${server.apiUrl}${api.products}/${id}`);
        if (!response.ok) {
            throw new Error(`Error fetching product: ${response.statusText}`);
        }
        return await response.json();
    } catch (error) {
        console.error(`Error fetching product with ID ${id}:`, error);
        throw error;
    }
}

/**
 * Crea un nuevo producto
 * @param {Object} productData - Datos del producto
 * @returns {Promise<Object>} - Promesa que resuelve al producto creado
 */
export async function createProduct(productData) {
    try {
        const response = await fetch(`${server.apiUrl}${api.products}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8'
            },
            body: JSON.stringify(productData)
        });
        
        if (!response.ok) {
            throw new Error(`Error creating product: ${response.statusText}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error('Error creating product:', error);
        throw error;
    }
}

/**
 * Actualiza un producto existente
 * @param {number} id - ID del producto
 * @param {Object} productData - Datos del producto
 * @returns {Promise<Object>} - Promesa que resuelve al producto actualizado
 */
export async function updateProduct(id, productData) {
    Object.assign(productData, { id });
    try {
        const response = await fetch(`${server.apiUrl}${api.products}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8'
            },
            body: JSON.stringify(productData)
        });
        
        if (!response.ok) {
            throw new Error(`Error updating product: ${response.statusText}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error(`Error updating product with ID ${id}:`, error);
        throw error;
    }
}

/**
 * Elimina un producto
 * @param {number} id - ID del producto
 * @returns {Promise<void>} - Promesa que resuelve cuando se completa la eliminación
 */
export async function deleteProduct(id) {
    try {
        const response = await fetch(`${server.apiUrl}${api.products}/${id}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            const error = await response.json();
            throw new Error(`Error deleting product: ${error.message || response.statusText}`);
        }
        
        return true;
    } catch (error) {
        console.error(`Error deleting product with ID ${id}:`, error);
        throw error;
    }
}

/**
 * Obtiene todas las marcas
 * @returns {Promise<Array>} - Promesa que resuelve a un array de marcas
 */
export async function getAllBrands() {
    try {
        const response = await fetch(`${server.apiUrl}${api.brands}`);
        if (!response.ok) {
            throw new Error(`Error fetching brands: ${response.statusText}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching brands:', error);
        throw error;
    }
}

/**
 * Obtiene todas las categorías
 * @returns {Promise<Array>} - Promesa que resuelve a un array de categorías
 */
export async function getAllCategories() {
    try {
        const response = await fetch(`${server.apiUrl}${api.categories}`);
        if (!response.ok) {
            throw new Error(`Error fetching categories: ${response.statusText}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching categories:', error);
        throw error;
    }
}

/**
 * Crea una nueva marca
 * @param {Object} brandData - Datos de la marca
 * @returns {Promise<Object>} - Promesa que resuelve a la marca creada
 */
export async function createBrand(brandData) {
    try {
        const response = await fetch(`${server.apiUrl}${api.brands}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8'
            },
            body: JSON.stringify(brandData)
        });
        
        if (!response.ok) {
            throw new Error(`Error creating brand: ${response.statusText}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error('Error creating brand:', error);
        throw error;
    }
}

/**
 * Sube un archivo
 * @param {File} file - Archivo a subir
 * @returns {Promise<string>} - Promesa que resuelve a la ruta del archivo subido
 */
export async function uploadFile(file) {
    try {
        const formData = new FormData();
        formData.append('file', file);
        
        const response = await fetch(`${server.apiUrl}${api.upload}`, {
            method: 'POST',
            body: formData
        });
        
        if (!response.ok) {
            throw new Error(`Error uploading file: ${response.statusText}`);
        }
        
        const data = await response.json();
        return data.filePath;
    } catch (error) {
        console.error('Error uploading file:', error);
        throw error;
    }
}
