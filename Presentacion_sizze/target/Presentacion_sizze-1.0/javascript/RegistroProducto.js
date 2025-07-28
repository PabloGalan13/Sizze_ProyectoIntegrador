/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
const modalExito = document.getElementById("modalExito");
const cerrarExito = document.getElementById("cerrarExito");

const modalError = document.getElementById("modalError");
const mensajeError = document.getElementById("mensajeError");
const cerrarError = document.getElementById("cerrarError");

class ProductoForm {
    constructor() {
        this.categoriaSelect = document.getElementById('categoria');
        this.tipoSelect = document.getElementById('tipo');
        this.nombre = document.getElementById('nombre');
        this.cantidad = document.getElementById('cantidad');
        this.talla = document.getElementById('talla');
        this.costo = document.getElementById('costo');
        this.imagen = document.getElementById('imgPortada');

        this.init();
    }

    init() {
        this.actualizarTipo();
        this.categoriaSelect.addEventListener('change', () => this.actualizarTipo());
        this.tipoSelect.addEventListener('change', () => this.actualizarPlaceholder());
        document.querySelector('.confirmar').addEventListener('click', e => this.validarFormulario(e));
        document.querySelector('.cancelar').addEventListener('click', () => this.resetFormulario());


        this.mostrarMensaje();
    }

    actualizarTipo() {
        const categoria = this.categoriaSelect.value;
        let opciones = [];

        switch (categoria) {
            case 'Ropa':
                opciones = ['Camiseta', 'Pantalón', 'Zapatos'];
                break;
            case 'Mica':
                opciones = ['Mica protectora', 'Mica curvada', 'Mica completa'];
                break;
            case 'Accesorios':
                opciones = ['Funda', 'Cargador', 'Audífonos'];
                break;
            case 'Electronica':
                opciones = ['Telefonos', 'Cables', 'Cargadores'];
                break;
        }

        this.tipoSelect.innerHTML = '';
        opciones.forEach(opcion => {
            const option = document.createElement('option');
            option.value = opcion;
            option.textContent = opcion;
            this.tipoSelect.appendChild(option);
        });

        this.actualizarPlaceholder();
    }

    actualizarPlaceholder() {
        const categoria = this.categoriaSelect.value;
        const tipo = this.tipoSelect.value;

        if (categoria === 'Ropa') {
            this.setPlaceholders("Ej: Camiseta blanca, Jeans", "Ej: 10 unidades", "Ej: S, M, L, XL", "Ej: 250.00 MXN");
        } else if (categoria === 'Micas') {
            this.setPlaceholders("Ej: Mica 9H para iPhone", "Ej: 20 piezas", "Ej: iPhone 13, Galaxy S22", "Ej: 150.00 MXN");
        } else if (categoria === 'Accesorios') {
            switch (tipo) {
                case 'Funda':
                    this.setPlaceholders("Ej: Funda de silicona roja", "Ej: 15 unidades", "Ej: iPhone 13, Xiaomi", "Ej: 180.00 MXN");
                    break;
                case 'Cargador':
                    this.setPlaceholders("Ej: Cargador rápido 20W", "Ej: 5 unidades", "Ej: Tipo C, Micro USB", "Ej: 300.00 MXN");
                    break;
                case 'Audífonos':
                    this.setPlaceholders("Ej: Audífonos Bluetooth", "Ej: 8 pares", "Ej: Inalámbricos, Alámbricos", "Ej: 500.00 MXN");
                    break;
                default:
                    this.setPlaceholders("Ej: Accesorio para teléfono", "Ej: 10 unidades", "Ej: Modelo del dispositivo", "Ej: 200.00 MXN");
                    break;
            }
        }
    }

    setPlaceholders(nombre, cantidad, talla, costo) {
        this.nombre.placeholder = nombre;
        this.cantidad.placeholder = cantidad;
        this.talla.placeholder = talla;
        this.costo.placeholder = costo;
    }

    validarFormulario(event) {
        const nombre = this.nombre.value.trim();
        const cantidad = this.cantidad.value.trim();
        const talla = this.talla.value.trim();
        const costo = this.costo.value.trim();
        const tipo = this.tipoSelect.value.trim();
        const categoria = this.categoriaSelect.value.trim();
        const imagenCargada = this.imagen.files.length > 0;

        if (!nombre || !cantidad || !talla || !costo || !tipo || !categoria || !imagenCargada) {
            alert('Por favor, completa todos los campos antes de continuar.');
            event.preventDefault();
            return;
        }

        // ✅ Aquí va la validación de números
        if (!/^\d+(\.\d{1,2})?$/.test(costo)) {
            alert('No se permiten letras en el campo Costo. Solo números (puedes usar punto decimal).');
            event.preventDefault();
            return;
        }
        
    }

    resetFormulario() {
        document.querySelector('form').reset();
        this.actualizarTipo();
    }

    mostrarMensaje() {
        const params = new URLSearchParams(window.location.search);
        if (params.has('mensaje')) {
            const mensaje = params.get('mensaje');

            if (mensaje === 'exito') {
                modalExito.classList.remove('hidden');
            } else if (mensaje === 'error') {
                modalError.classList.remove('hidden');
                mensajeError.textContent = 'Hubo un error al registrar el producto. Inténtalo de nuevo.';
            }

            // Limpiar parámetros de la URL
            params.delete('mensaje');
            const nuevaURL = window.location.pathname + (params.toString() ? '?' + params.toString() : '');
            window.history.replaceState({}, '', nuevaURL);
        }

        // Botones para cerrar los modales
        cerrarExito.addEventListener('click', () => {
            modalExito.classList.add('hidden');
        });

        cerrarError.addEventListener('click', () => {
            modalError.classList.add('hidden');
        });
    }
}

window.addEventListener('load', () => new ProductoForm());
