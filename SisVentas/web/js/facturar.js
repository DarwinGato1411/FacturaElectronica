const calcWidth = (elementNode) => {
  const element = document.querySelector(elementNode);
  const width = `${window.innerWidth}px`;
  element.style.width = width;
  element.previousSibling.style.width = width;
};

(() => {
  zk.afterMount(function () {
    calcWidth(".tabpanels");
  });
})();
