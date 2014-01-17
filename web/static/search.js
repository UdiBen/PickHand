/**
 * Created with IntelliJ IDEA.
 * User: amit
 * Date: 17/01/14
 * Time: 14:29
 * To change this template use File | Settings | File Templates.
 */
$(function () {
    var scope = $('#search-container');
    $('.search-button').on('click', function () {
        var player = scope.find(".player").val();
        var hand = scope.find(".hand").val();

        $.ajax({
            type:"GET",
            url:"/search",
            data:{
                player:player,
                hand:hand
            }
        }).done(function (data) {
                alert(data);
        });
    });
});