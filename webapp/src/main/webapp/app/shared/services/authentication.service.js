/**
 * Created by Denis on 3/11/2017.
 */
define(
    [], function () {

        function AuthenticationService($q){
            return {
                authenticated: false,
                login: function(){
                    return $q.resolve(true);
                },
                logout: function(){
                    return $q.resolve(true);
                },
                isAuthenticated: function(){
                    return this.authenticated;
                },
                register: function(){
                    return true;
                }
            }
        }

        AuthenticationService.$inject = ['$q'];

        return AuthenticationService;
    }
);
