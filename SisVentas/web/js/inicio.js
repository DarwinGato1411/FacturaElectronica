const deleteTags = (node) => {
  /*
   *  Se eliminan todas las etiquetas que contengan z-label
   *    en el nodo padre pasado por argumento
   */

  const container = document.querySelector(node);

  const list = Array.from(container.querySelectorAll(".z-label"));
  list.forEach((e) => e.classList.remove("z-label"));
};

const openLogin = () => {
  const login = document.querySelector(".btnLogin");
  const modal = document.querySelector(".login_wrapper");
  const loginContainer = document.querySelector(".login_container_id");
  const forgot = document.querySelector(".forgot");

  const openModal = () => {
    loginContainer.style.display = "flex";
  };

  const closeModal = (e) => {
    if (!modal.contains(e.target) && e.target !== login) {
      loginContainer.style.display = "none";
    }
  };
  login.addEventListener("click", openModal);
  document.addEventListener("click", (e) => closeModal(e));
  forgot.addEventListener("click", () => {
    loginContainer.style.display = "none";
  });
};

const showPassword = () => {
  const password = document.querySelector(".input_password");
  const icon = document.querySelector(".icon_eye");

  const toggleShowPass = () => {
    if (password.type === "password") {
      password.type = "text";
      icon.classList.remove("fa-eye");
      icon.classList.add("fa-eye-slash");
    } else {
      password.type = "password";
      icon.classList.remove("fa-eye-slash");
      icon.classList.add("fa-eye");
    }
  };

  icon.addEventListener("click", toggleShowPass);
};

const stopLoading = () => {
  const loading = document.querySelector(".loading_inicio");
  loading.style.display = "none";
};

(() => {
  zk.afterMount(function () {
    setTimeout(() => {
      deleteTags(".login_container"); //inicio.zul
      showPassword();
      stopLoading();
    }, 2000);
  });
})();
