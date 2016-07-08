function ScrollToContent() {
    document.getElementById("content").style.visibility = "visible";
    $("#content").fadeIn(300);
    $('html, body').animate({
                scrollTop: ($('#content').offset().top-70)
            }, 'slow');
}

$(document).ready( function() {

    $("#p1_a").click(function() {
        $("#content").load("./1_a.html");
        // $("#content").scrollTop(0);
        $(this).addClass("highlight");
        $("#p1_b,#p1_c,#p1_d,#p1_e,#p1_f,#p1_g,#p1_h,#p1_i").removeClass("highlight");
        ScrollToContent();
    });
    $("#p1_b").click(function() {
        $("#content").load("./1_b.html");
        // $("#content").scrollTop(0);
        $(this).addClass("highlight");
        $("#p1_a,#p1_c,#p1_d,#p1_e,#p1_f,#p1_g,#p1_h,#p1_i").removeClass("highlight");
        // $("body").scrollTop($("#content").position().top);
        ScrollToContent();
        // document.getElementById("content").style.display = "";
    });
    $("#p1_c").click(function() {
        $("#content").load("./1_c.html");
        // $("#content").scrollTop(0);
        $(this).addClass("highlight");
        $("#p1_b,#p1_a,#p1_d,#p1_e,#p1_f,#p1_g,#p1_h,#p1_i").removeClass("highlight");
        // $("body").scrollTop($("#content").position().top);
        ScrollToContent();
    });
    $("#p1_d").click(function() {
        $("#content").load("./1_d.html");
        // $("#content").scrollTop(0);
        $(this).addClass("highlight");
        $("#pp1_a,#p1_c,#p1_a,#p1_e,#p1_f,#p1_g,#p1_h,#p1_i").removeClass("highlight");
        // $("body").scrollTop($("#content").position().top);
        ScrollToContent();
    });
    $("#p1_e").click(function() {
        $("#content").load("./1_e.html");
        // $("#content").scrollTop(0);
        $(this).addClass("highlight");
        $("#p1_b,#p1_c,#p1_d,#p1_a,#p1_f,#p1_g,#p1_h,#p1_i").removeClass("highlight");
        // $("body").scrollTop($("#content").position().top);
        ScrollToContent();
    });
    $("#p1_f").click(function() {
        $("#content").load("./1_f.html");
        // $("#content").scrollTop(0);
        $(this).addClass("highlight");
        $("#p1_a,#p1_c,#p1_d,#p1_e,#p1_a,#p1_g,#p1_h,#p1_i").removeClass("highlight");
        // $("body").scrollTop($("#content").position().top);
        ScrollToContent();
    });
    $("#p1_g").click(function() {
        $("#content").load("./1_g.html");
        // $("#content").scrollTop(0);
        $(this).addClass("highlight");
        $("#p1_b,#p1_c,#p1_d,#p1_e,#p1_f,#p1_a,#p1_h,#p1_i").removeClass("highlight");
        // $("body").scrollTop($("#content").position().top);
        ScrollToContent();
    });
    $("#p1_h").click(function() {
        $("#content").load("./1_h.html");
        // $("#content").scrollTop(0);
        $(this).addClass("highlight");
        $("#p1_b,#p1_c,#p1_d,#p1_e,#p1_f,#p1_g,#p1_a,#p1_i").removeClass("highlight");
        // $("body").scrollTop($("#content").position().top);
        ScrollToContent();
    });
    $("#p1_i").click(function() {
        $("#content").load("./1_i.html");
        // $("#content").scrollTop(0);
        $(this).addClass("highlight");
        $("#p1_b,#p1_c,#p1_d,#p1_e,#p1_f,#p1_g,#p1_h,#p1_a").removeClass("highlight");
        // $("body").scrollTop($("#content").position().top);
        ScrollToContent();
    });

    var scriptString = "You're logged out!";
    $('.SignOut').click(function(){
        $.ajax({
          method: 'get',
          url: 'kickout.php',
          data: {
            'myString': scriptString,
            'ajax': true
          },
          // success: function(data) {
          //   $('#controls').text(data);
          // }
        });
        // $("#Status").fadeOut(200);
        // $("#TaskHolder").fadeOut(200);
        // $("#content").fadeOut(200);
        $("#controls").load("./loginRGDX.php");
        $("#controls").animate({height:'130px'},"slow");
    });
});
// function loadPage(urlToLoad) {
//     $('#main').load(urlToLoad, function () {
//         alert('Load was performed.');
//     });
// }
// <a onclick="loadPage('load.php');" href="javascript:void(0)" class="nav">