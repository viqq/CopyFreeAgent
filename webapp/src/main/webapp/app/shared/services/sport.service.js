/**
 * Created by Denis on 3/10/2017.
 */
define(
    [], function () {
        var SportService = function ($http){
            return {
                getSports: function(){
                    return ['sport1', 'sport2', 'sport3'];
                },
                addSport: function () {
                    return function(data) {
                        var d = $http({
                            method: 'POST',
                            url: '/api/admin/sport',
                            data: data,
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded'
                            }
                        });

                        d.success(function(data) {
                            console.log(data)
                        }).error(function(err) {
                            console.log(err);
                        });

                        return d;
                    }
                }
            }
        };

        SportService.$inject = ['$http'];

        return SportService;
    }
);