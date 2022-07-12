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

document.getElementById('novolivro_enviar').onclick = (e) => {
    e.preventDefault();
    const name = document.getElementById('novoLivro_name').value;

    axios.post('http://localhost:8000/api/createbook', {}, {
        params: {
            nome: name,
        }
    })
}

document.getElementById('consultarlivro_enviar').onclick = (e) => {
    e.preventDefault();
    const name = document.getElementById('consultarlivro_name').value;

    axios.get('http://localhost:8000/api/readbooks', {
        params: {
            nome: name,
        }
    }).then((data) => {
        const inner = data.data.items.reduce((prev, val) => {
            return prev + '<div>' + val.id.toString().padStart(3, '0') + val.nome.padStart(20, '_') + "_" + (val.disponibilidade === 'true' ? '__Sim__' : '__Não__') + '</div>'
        }, `<div>${'id'.padStart(3, '_')}${'nome'.padStart(20, '_')}${'_Dispon.'}</div>`)
        document.getElementById("consultarlivro_resposta").innerHTML = inner;
    })
}

document.getElementById('novoemprestimo_enviar').onclick = (e) => {
    e.preventDefault();
    const idLivro = document.getElementById('novoEmprestimo_IDLivro').value;
    const idUsuario = document.getElementById('novoEmprestimo_IDUsuario').value;

    axios.post('http://localhost:8000/api/createemprestimo', {}, {
        params: {
            idUsuario,
            idLivro
        }
    })
}

document.getElementById('devolveremprestimo_enviar').onclick = (e) => {
    e.preventDefault();
    const idLivro = document.getElementById('devolverEmprestimo_IDLivro').value;
    const idUsuario = document.getElementById('devolverEmprestimo_IDUsuario').value;

    axios.post('http://localhost:8000/api/devolveremprestimo', {}, {
        params: {
            idUsuario,
            idLivro
        }
    })
}