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
});
