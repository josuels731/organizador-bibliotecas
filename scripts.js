document.getElementById('novousuario_enviar').onclick = (e) => {
    e.preventDefault();
    const name = document.getElementById('novousuario_name').value;
    const address = document.getElementById('novousuario_address').value;
    const password = document.getElementById('novousuario_password').value;
    const admin = document.getElementById('novousuario_admin').checked ? 1 : 0;

    axios.post('http://localhost:8000/api/createuser', {}, {
        params: {
            nome: name,
            endereco: address,
            admin,
            senha: password
        }
    })
}

document.getElementById('loginusuario_enviar').onclick = (e) => {
    e.preventDefault();
    const name = document.getElementById('loginusuario_name').value;
    const password = document.getElementById('loginusuario_password').value;

    axios.post('http://localhost:8000/api/login', {}, {
        params: {
            nome: name,
            senha: password
        }
    }).then(() => {
        document.getElementById('loginusuario_status').textContent = 'Sucesso';
    }).catch(() => {
        document.getElementById('loginusuario_status').textContent = 'Falha';
    })
}