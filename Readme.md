# Testes da API ServeRest


Este projeto contém testes automatizados para a API [ServeRest](https://serverest.dev/), desenvolvidos em Java com JUnit 5. Os testes abrangem cenários de listagem, registro, login e edição de usuários, incluindo casos de sucesso e falha.

---

## 📋Suíte de Testes API - Serverest

### Feature: Teste de Cadastro de Usuário
- Deve realizar cadastro com sucesso
- Deve falhar ao realizar cadastro com e-mail inválido
- Deve falhar ao realizar cadastro com nome em branco
- Deve falhar ao realizar cadastro com email em branco
- Deve falhar ao realizar cadastro com senha em branco

### Feature: Teste de Login de Usuário
- Deve realizar login com sucesso
- Não deve realizar login com email inválido
- Não deve realizar login com email vazio
- Não deve realizar login com senha inválida
- Não deve realizar login com senha vazia
- Não deve realizar login com email e senha vazios
- Não deve realizar login com email e senha com espaços em branco
- Não deve realizar login com email nulo
- Não deve realizar login com senha nula

### Feature: Teste de Obtenção de Usuário
- Deve obter um usuário na lista

### Feature: Testes de Edição de Usuário
- Deve realizar edição com sucesso
- Deve falhar ao realizar edição com todos os dados em branco
- Deve criar um novo usuário ao tentar editar um usuário inexistente
- Deve falhar ao realizar edição com campos nulos

---

## Tecnologias e Ferramentas

- [Java 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
- [Maven](https://maven.apache.org/)
- [JUnit 5](https://junit.org/junit5/)
- [RestAssured](https://rest-assured.io/)
- [Faker](https://github.com/DiUS/java-faker) (gerador de dados)
- [Allure](https://docs.qameta.io/allure/) (relatórios de testes) 👉 Guia de instalação: [Allure Report](https://allurereport.org/docs/install-for-windows/)
- [GitLab CI/CD](https://docs.gitlab.com/ee/ci/) (integração contínua)

---

## 💻 Instalação

Com as dependências listadas acima instaladas, siga os passos abaixo para rodar o projeto localmente:

1. **Clone o repositório:**

```bash
    git clone https://github.com/juliosantosjob/ServiceDev-RestAssurance-Junit.git
```

2. **Navegue até o diretório do projeto:**

```bash
    cd ServiceDev-RestAssurance-Junit
```

3. **Instale as dependências do projeto usando o Maven:**
```bash
    mvn clean install
```

---

## Estrutura do Projeto

## ⚠️ Configuração de Dados Sensíveis

Para rodar o projeto é extramente importante seguir estes passos. É **obrigatório** criar um arquivo com nome: `secrets.properties`, informando credenciais válidas  e URL da aplicação.

Já existe um arquivo de exemplo no caminho `src/test/resources/config.properties.example`



Basta criar o arquivo `config.properties` no mesmo diretório `src/test/resources`. No arquivo `config.properties.example` tem todas as instruções do que deve ser preenchido.

### Exemplo:

```properties
# Ambiente de Homologação
BASE_URL=url_servicedev_aqui
```

Eu criei esta implementação para garantir que dados sensíveis não sejam expostos no repositório, mantendo a segurança das credenciais.

Feito isso, o projeto estará pronto para ser executado localmente. Você pode rodar os testes usando o Maven:
```bash
    mvn test
```

ou executar a classe `TestRunner` que se encontra no caminho `src/test/java/automation/dev/serverest/api/runners/TestRunner.java`.
desta maneira você pode executar todos os testes ou apenas uma suíte específica dependendo da tag que você informar.

### 📊 Reportes de Testes

O projeto gera relatórios com Allure Reports.

1. Executando Localmente: ao executar os testes é criada a pasta allure-results.
   Para visualizar o resultado dos testes, rode o comando:

```bash
    allure serve
```

2. Via Pipeline: os relatórios são gerados automaticamente na pipeline do Github actions.
   para visualizar a ultima execução, basta acessar clicar [aqui.](https://juliosantosjob.github.io/ServiceDev-RestAssurance-Junit/625/index.html)

## 🌐 Redes:

[![Email](https://img.shields.io/badge/Email-%23D14836.svg?logo=gmail&logoColor=white)](mailto:julio958214@gmail.com)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-%230077B5.svg?logo=linkedin&logoColor=white)](https://www.linkedin.com/in/julio-santos-43428019b)
[![Facebook](https://img.shields.io/badge/Facebook-%231877F2.svg?logo=Facebook&logoColor=white)](https://www.facebook.com/profile.php?id=100003793058455)
[![Instagram](https://img.shields.io/badge/Instagram-%23E4405F.svg?logo=Instagram&logoColor=white)](https://www.instagram.com/oficial_juliosantos/)
[![Discord](https://img.shields.io/badge/Discord-%237289DA.svg?logo=discord&logoColor=white)](https://discord.gg/julio.saantos199)
