/**
 * Created by Denis on 3/10/2017.
 */
define(
    [], function () {
        var UserService = function ($http){
            return {
                getInfo: function(){
                    return 'some info';
                },
                editInfo: function (data, id) {
                    if (typeof id !== 'number' || !id && id !== 0) {
                        return;
                    }

                    var d = $http({
                        method: 'POST',
                        url: '/api/user/' + id,
                        data: data,
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                        }
                    });

                    d.success(function (data) {
                        console.log(data)
                    }).error(function (err) {
                        console.log(err);
                    });

                    return d;

                }
            }
        };

        UserService.$inject = ['$http'];

        return UserService;
    }
);