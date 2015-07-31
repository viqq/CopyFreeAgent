/**
 * Created by TITUS on 25.07.2015.
 */
define(['jquery'], function ($) {
    var initLoginPage = function () {
        $('#login_btn').on('click', function (event) {
            $.ajax({
                url: '../j_spring_security_check',
                method: 'POST',
                data: $('#login_form').serialize(),
                success: function (res, status, xhr) {
                    var isRegFailed = xhr.responseText.indexOf('<b class="error">Invalid login or password.</b>');

                    if (isRegFailed) {
                        alert('Login failed');
                    }
                }
            });
        });
    };

    return initLoginPage;
});