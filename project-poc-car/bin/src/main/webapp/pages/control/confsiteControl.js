var app = angular.module('confsiteModule', [
		'angularUtils.directives.dirPagination', 'ui.mask', 'luk.money' ]);

app.directive('ngFiles', [ '$parse', function($parse) {

	function fn_link(scope, element, attrs) {
		var onChange = $parse(attrs.ngFiles);
		element.on('change', function(event) {
			onChange(scope, {
				$files : event.target.files
			});
		});
	}
	;

	return {
		link : fn_link
	}
} ]);

app
		.controller(
				'confsiteControl',
				function($scope, $http, $filter) {

					urlTipoConfSite = 'http://localhost:7001/project-poc-car/confsite/tipo-confsite-lista';
					urlConfSite = 'http://localhost:7001/project-poc-car/confsite/confsite-lista';
					urlGerarConfSiteTI = 'http://localhost:7001/project-poc-car/confsite/gerar-confsite-ti';

					$scope.listaArquivo = [];
					$scope.carregarArquivos = function($files) {
						angular.forEach($files,
								function(value, key) {
									if (!$scope.listaArquivo.find(
											arquivoExiste, value))
										getFileBase64(value);
									// $scope.listaArquivo.push(value);
								});
					};

					function arquivoExiste(arqArray) {
						// arqArray é o arquivo que já está no array
						// this é o arquivo que está sendo adicionado
						return arqArray.nome == this.nome;
					}

					$scope.excluirArquivo = function(arquivo) {
						var index = $scope.listaArquivo.indexOf(arquivo);
						$scope.listaArquivo.splice(index, 1);
						$scope.listaArquivo = $scope.listaArquivo;
					}

					$scope.adicionarArquivo = function() {
						$scope.listaArquivo = $scope.listaArquivo;
						document.getElementById("fileUpload").value = null;
					};

					$scope.carregarTiposConfSite = function() {
						$http
								.get(urlTipoConfSite)
								.success(
										function(listaTipoConfsite) {
											$scope.listaTipoConfsite = listaTipoConfsite;
										}).error(function(erro) {
									alert(erro);
								});
					}

					$scope.carregarConfSite = function() {
						$http.get(urlConfSite).success(
								function(listaConfsite) {

									angular.forEach(listaConfsite, function(
											value, key) {
										value.dtCriacao = new Date(
												value.dtCriacao);
									})

									$scope.listaConfsite = listaConfsite;
								}).error(function(erro) {
							alert(erro);
						});
					}

					function replaceAll(str, needle, replacement) {
						return str.split(needle).join(replacement);
					}

					$scope.montaMensagemErro = function(listaErro) {
						$scope.mensagens = [];
						$scope.mensagens
								.push('Falha de validação retornada do servidor');
						angular.forEach(listaErro, function(value, key) {
							$scope.mensagens.push(value.message);
						});
					}

					$scope.seleciona = function(confsite) {
						$scope.mensagens = [];
						$scope.confsite = confsite;
					}

					$scope.ordenar = function(keyname) {
						$scope.sortKey = keyname;
						$scope.reverse = !$scope.reverse;
					};

					$scope.gerarConfSiteTI = function() {
						$scope.mensagens = [];
						$scope.confsite.arquivo = $scope.listaArquivo;

						$http
								.post(urlGerarConfSiteTI, $scope.confsite)
								.success(function(result) {
									alert(result.mensagem);
								})
								.error(
										function(erro) {
											$scope
													.montaMensagemErro(erro.parameterViolations);
										});
					}

					function getFileBase64(file) {
						var reader = new FileReader();
						reader.readAsDataURL(file);

						reader.onload = function() {
							var arquivo = {};
							arquivo.name = file.name;
							arquivo.base64 = reader.result.split(",")[1];
							$scope.listaArquivo.push(arquivo);
						};
						reader.onerror = function(error) {
							console.log('Error: ', error);
						};
					}

					$scope.carregarTiposConfSite();
					$scope.carregarConfSite();

				});