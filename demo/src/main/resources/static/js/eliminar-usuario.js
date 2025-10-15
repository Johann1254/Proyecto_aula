document.addEventListener("DOMContentLoaded", () => {
  const tablaUsuarios = document.getElementById("dataTable");
  const alerta = document.getElementById("alertaConfirmacion");
  const overlay = document.getElementById("overlay");

  if (!tablaUsuarios || !alerta || !overlay) {
    console.error("⚠️ No se encontró algún elemento necesario (tabla, alerta u overlay)");
    return;
  }

  let idSeleccionado = null;
  let filaSeleccionada = null;

  // Mostrar alerta
  tablaUsuarios.addEventListener("click", (e) => {
    const btn = e.target.closest(".btn-eliminar");
    if (!btn) return;

    idSeleccionado = btn.dataset.id;
    filaSeleccionada = btn.closest("tr");

    overlay.style.display = "block";
    alerta.classList.remove("d-none");

    console.log("🟢 Mostrando alerta para usuario ID:", idSeleccionado);
  });

  // Cerrar alerta (botones .dismiss)
  alerta.querySelectorAll(".dismiss").forEach((boton) => {
    boton.addEventListener("click", () => {
      alerta.classList.add("d-none");
      overlay.style.display = "none";
      idSeleccionado = null;
      filaSeleccionada = null;
    });
  });

  // Confirmar eliminación
  const btnConfirmar = alerta.querySelector(".track");
  btnConfirmar.addEventListener("click", async () => {
    if (!idSeleccionado) return;

    try {
      const response = await fetch(`/api/usuarios/${idSeleccionado}`, {
        method: "DELETE",
      });

      if (response.ok) {
        filaSeleccionada.classList.add("fade-out");
        setTimeout(() => filaSeleccionada.remove(), 300);
        console.log(`✅ Usuario ${idSeleccionado} eliminado`);
      } else {
        alert("❌ No se pudo eliminar el usuario (error del servidor).");
      }
    } catch (error) {
      alert("⚠️ Error al conectar con el servidor.");
      console.error(error);
    }

    alerta.classList.add("d-none");
    overlay.style.display = "none";
  });
});
