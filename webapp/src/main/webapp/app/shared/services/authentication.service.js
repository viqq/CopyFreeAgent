/**
 * Created by Denis on 3/11/2017.
 */
define(
    [], function () {

        var AuthenticationService = function ($http, $q){
            return {
                authenticated: false,
                login: function(){
                    return $q.resolve(true);
                },
                logout: function(){
                    return $q.resolve(true);
                },
                isAuthenticated: function(){

                    var d = $http({
                        method: 'GET',
                        url: '/api/isLoggedIn'
                    });

                    d.success(function (data) {
                        console.log(data)
                    }).error(function (err) {
                        console.log(err);
                    });

                    return d;
                },
                register: function(){
                    return true;
                }
            }
        };

        AuthenticationService.$inject = ['$http','$q'];

        return AuthenticationService;
    }
);
