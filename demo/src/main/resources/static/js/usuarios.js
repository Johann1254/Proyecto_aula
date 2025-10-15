$(document).ready(function () {
  const tabla = $('#dataTable').DataTable({
    ajax: {
      url: '/api/usuarios', // usa la API REST
      dataSrc: '' // la API devuelve una lista
    },
    columns: [
      { data: 'id' },
      { data: 'usuario' },
      { data: 'correo' },
      {
        data: 'rol',
        render: function (data) {
          if (data === 'ROLE_ADMIN') return 'Administrador';
          if (data === 'ROLE_USER') return 'Usuario';
          return data;
        }
      },
      {
        data: 'activo',
        render: function (data) {
          return data
            ? '<span class="badge bg-success">Activo</span>'
            : '<span class="badge bg-danger">Inactivo</span>';
        }
      },
      {
        data: 'id',
        render: function (id) {
          return `
            <div class="text-center">
              <a href="/usuarios/editar/${id}" class="btn btn-sm btn-warning me-1">
                <i class="fas fa-edit"></i>
              </a>
              <button class="btn btn-sm btn-danger btn-eliminar" data-id="${id}">
                <i class="fas fa-trash-alt"></i>
              </button>
            </div>`;
        },
        orderable: false
      }
    ],
    language: {
      url: '//cdn.datatables.net/plug-ins/1.13.5/i18n/es-ES.json'
    }
  });

  // --- 🔥 NUEVA FUNCIÓN PARA ELIMINAR USUARIO ---
  $('#dataTable').on('click', '.btn-eliminar', function () {
    const id = $(this).data('id');
    mostrarAlertaEliminar(id, tabla);
  });
});


// --- 🔔 FUNCIÓN PARA MOSTRAR LA ALERTA PERSONALIZADA ---
function mostrarAlertaEliminar(id, tabla) {
  // Muestra tu alerta personalizada
  const overlay = document.getElementById('overlay');
  const alerta = document.getElementById('alertaConfirmacion');
  overlay.style.display = 'block';
  alerta.style.display = 'block';

  // Cuando el usuario confirma
  document.getElementById('confirmarEliminar').onclick = function () {
    $.ajax({
      url: `/api/usuarios/${id}`,
      type: 'DELETE',
      success: function () {
        ocultarAlerta();
        mostrarMensajeExito('✅ Usuario eliminado correctamente');
        tabla.ajax.reload();
      },
      error: function () {
        ocultarAlerta();
        mostrarMensajeError('❌ Error al eliminar el usuario');
      }
    });
  };

  // Cuando el usuario cancela
  document.getElementById('cancelarEliminar').onclick = function () {
    ocultarAlerta();
  };
}

// --- 🔕 FUNCIONES DE UTILIDAD ---
function ocultarAlerta() {
  document.getElementById('overlay').style.display = 'none';
  document.getElementById('alertaConfirmacion').style.display = 'none';
}

function mostrarMensajeExito(mensaje) {
  // Si tienes un contenedor para mensajes, úsalo.
  // O puedes crear un pequeño toast temporal:
  const toast = document.createElement('div');
  toast.textContent = mensaje;
  toast.className = 'toast-exito';
  document.body.appendChild(toast);
  setTimeout(() => toast.remove(), 2500);
}

function mostrarMensajeError(mensaje) {
  const toast = document.createElement('div');
  toast.textContent = mensaje;
  toast.className = 'toast-error';
  document.body.appendChild(toast);
  setTimeout(() => toast.remove(), 2500);
}
