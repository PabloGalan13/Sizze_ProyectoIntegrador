/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


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

        // Validar solo números en costo
        this.costo.addEventListener('input', () => {
            this.costo.value = this.costo.value.replace(/[^0-9.]/g, '');
        });

        this.mostrarMensaje();
    }

    actualizarTipo() {
        const categoria = this.categoriaSelect.value;
        let opciones = [];

        switch (categoria) {
            case 'Ropa':
                opciones = ['Camiseta', 'Pantalón', 'Zapatos'];
                break;
            case 'Micas':
                opciones = ['Mica protectora', 'Mica curvada', 'Mica completa'];
                break;
            case 'Accesorios':
                opciones = ['Funda', 'Cargador', 'Audífonos'];
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
                alert('Producto registrado exitosamente.');
            } else if (mensaje === 'error') {
                alert('Hubo un error al registrar el producto. Inténtalo de nuevo.');
            }

            // ❌ Quitar el parámetro 'mensaje' de la URL
            params.delete('mensaje');
            const nuevaURL = window.location.pathname + (params.toString() ? '?' + params.toString() : '');
            window.history.replaceState({}, '', nuevaURL);
        }
    }
}

window.addEventListener('load', () => new ProductoForm());
