# Documentation Website

This documentation website is built using [Docusaurus 2](https://docusaurus.io/), a modern static
website generator.

### Installation

Before you can develop, build, or deploy the documentation website, you must install the
dependencies:

```shell
$ yarn
```

### Local Development

```shell
$ yarn start
```

This command starts a local development server and opens up a browser window. Most changes are
reflected live without having to restart the server.

### Build

```shell
$ yarn build
```

This command generates static content into the `build` directory and can be served using any static
contents hosting service.

### Deployment

Using SSH:

```shell
$ USE_SSH=true yarn deploy
```

Not using SSH:

```shell
$ GIT_USER=<Your GitHub username> yarn deploy
```

If you are using GitHub pages for hosting, this command is a convenient way to build the website and
push to the `gh-pages` branch.