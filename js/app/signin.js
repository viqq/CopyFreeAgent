/**
 * Created by antonPC on 20.06.15.
 */
$(document).ready(function() {
    $('#submitButton').submit(function() {
        $.ajax({
            url: "/signin/login",
            data: $('#singin').serialize(),
            success: function(){
                var i;
            }
        });

    });
});