

var app = angular.module('carModule', [
		'angularUtils.directives.dirPagination', 'ui.mask', 'luk.money','pascalprecht.translate']);
		
app.config(['$translateProvider', function ($translateProvider) {
  $translateProvider.translations('en', translationsEN);
  $translateProvider.translations('pt', translationsPT);
  $translateProvider.fallbackLanguage('pt');
  $translateProvider.preferredLanguage('pt');
}]);				

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
				'carControl',
				function($scope, $http, $filter, $translate) {

					urlSave = 'http://localhost:7001/project-poc-car/car/save';
					urlDelete = 'http://localhost:7001/project-poc-car/car/delete';
					urlListAllCar = 'http://localhost:7001/project-poc-car/car/list-all-cars';
					

					$scope.listAllCars = function() {
						$http.get(urlListAllCar).success(
								function(listCar) {

									angular.forEach(listCar, function(value, key) {
										value.registrationDate = new Date(value.registrationDate);
										value.updateDate = new Date(value.updateDate);
										if (value.price != undefined && value.price != '') {
											value.price = parseFloat(value.price).toFixed(2);
										}
									})

									$scope.listCar = listCar;
								}).error(function (erro) {				
									$scope.montaMensagemGenerica(erro.parameterViolations);
								});
					}
					
					$(function(){
					    $('.spinner .btn:first-of-type').on('click', function() {
					      var btn = $(this);
					      var input = btn.closest('.spinner').find('input');
					      if (input.attr('max') == undefined || parseInt(input.val()) < parseInt(input.attr('max'))) {    
					        input.val(parseInt(input.val(), 10) + 1);
					      } else {
					        btn.next("disabled", true);
					      }
					    });
					    $('.spinner .btn:last-of-type').on('click', function() {
					      var btn = $(this);
					      var input = btn.closest('.spinner').find('input');
					      if (input.attr('min') == undefined || parseInt(input.val()) > parseInt(input.attr('min'))) {    
					        input.val(parseInt(input.val(), 10) - 1);
					      } else {
					        btn.prev("disabled", true);
					      }
					    });

					})
					
					$(function(){ 
						$(document).on( 'scroll', function(){					 
							if ($(window).scrollTop() > 100) {
								$('.smoothscroll-top').addClass('show');
							} else {
								$('.smoothscroll-top').removeClass('show');
							}
						});
					 
						$('.smoothscroll-top').on('click', scrollToTop);
					});
 
					function scrollToTop() {
						verticalOffset = typeof(verticalOffset) != 'undefined' ? verticalOffset : 0;
						element = $('body');
						offset = element.offset();
						offsetTop = offset.top;
						$('html, body').animate({scrollTop: offsetTop}, 600, 'linear').animate({scrollTop:25},200).animate({scrollTop:0},150) .animate({scrollTop:0},50);
					}
					
					
					function replaceAll(str, needle, replacement) {
						return str.split(needle).join(replacement);
					}

					$scope.montaMensagemGenerica = function(listaErro) {
						$scope.mensagens = [];
						$scope.mensagens
								.push('Falha de validação retornada do servidor');
						angular.forEach(listaErro, function(value, key) {
							$scope.mensagens.push(value.message);
						});
					}

					$scope.select = function(car) {
						$scope.mensagens = [];
						var formatUpdateDate = $filter('date')(car.updateDate, 'dd/MM/yyyy', 'UTC');
						var formatRegistrationDate = $filter('date')(car.registrationDate, 'dd/MM/yyyy', 'UTC');
						car.updateDate = formatUpdateDate;
						car.registrationDate = formatRegistrationDate;
						$scope.car = car;
					}

					$scope.ordenar = function(keyname) {
						$scope.sortKey = keyname;
						$scope.reverse = !$scope.reverse;
					};

					$scope.save = function() {
						$scope.mensagens = [];
						$scope.prepararCampos();
						
						if($scope.formPrincipal.$invalid){
							$scope.mensagens.push('Campo(s) Obrigatório(s) não Informado(s)!');
						}else{
							$http
									.post(urlSave, $scope.car)
									.success(function(result) {										
									    $scope.novo();
										$scope.mensagens.push(result);
									})
									.error(
											function(erro) {
												$scope
														.montaMensagemGenerica(erro.parameterViolations);
											});
						}				
					}
					
					$scope.prepararCampos = function() {
						if ($scope.car.price != undefined && $scope.car.price != '') {
							if($scope.car.price.toString().indexOf(',') != -1){
								$scope.car.price = $scope.car.price.replace('.','');
								$scope.car.price = $scope.car.price.replace(',','.');
							}
						}
						
						if ($scope.car.updateDate != undefined && $scope.car.updateDate != '') {
							$scope.car.updateDate = replaceAll($scope.car.updateDate.toString(),'/','');
							var ano = $scope.car.updateDate.substring(4, 8);
							var mes = $scope.car.updateDate.substring(2, 4);
							var dia = $scope.car.updateDate.substring(0, 2);
							$scope.car.updateDate = ano + '-' + mes + '-' + dia;
						}
						
						if ($scope.car.registrationDate != undefined && $scope.car.registrationDate != '') {
							$scope.car.registrationDate = replaceAll($scope.car.registrationDate.toString(),'/','');
							var ano = $scope.car.registrationDate.substring(4, 8);
							var mes = $scope.car.registrationDate.substring(2, 4);
							var dia = $scope.car.registrationDate.substring(0, 2);
							$scope.car.registrationDate = ano + '-' + mes + '-' + dia;
						}						
					}
					
					$scope.delete = function() {
						$scope.mensagens = [];
						$scope.prepararCampos();
						
						if ($scope.car.id == undefined || $scope.car.id == '') {
							$scope.mensagens.push('Favor selecionar um Veículo antes de clicar no botão Excluir!');
						} else {
							$http.post(urlDelete, $scope.car).success(function(result) {
								if ($scope.car.id != undefined && $scope.car.id != '') {
									$scope.novo();
									$scope.mensagens.push('Registro excluído com sucesso!');
								}else{
									$scope.mensagens.push('Favor selecionar um Veículo antes de clicar no botão Excluir!');
								}
							}).error(function(erro){
								$scope.montaMensagemGenerica(erro.parameterViolations);
							});
						}
					}
					
					$scope.novo = function() {
						$scope.car = {};
						$scope.mensagens = [];
						$scope.car.isNew = false;
						$scope.getLastYear();
					}
					
					$scope.getLastYear = function() {
						var mydate = new Date();
						var year = mydate.getYear()
						if (year < 1000){
							year += 1900
						}
						$scope.car.year = year;
					}
					
					$scope.setCurrentLanguage = function(language) {
						$translate.use(language);
					};

					$scope.listAllCars();
					$scope.novo();
					
        });
				

				