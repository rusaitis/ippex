function ScrollToContent() {
    document.getElementById("content").style.visibility = "visible";
    $("#content").fadeIn(300);
    $('html, body').animate({
                scrollTop: ($('#content').offset().top-70)
            }, 'slow');
}

$(document).ready( function() {

    $('#nav > li > a').click(function(){
      if ($(this).attr('class') != 'open'){
        $('#nav li ul').slideUp({duration: "slow", easing:"easeOutBack"});
        $(this).next().slideToggle({duration: "slow", easing:"easeOutBack"});
        $('#nav li a').removeClass('open');
        $(this).addClass('open');
      }else{
        $(this).next().slideToggle({duration: "slow", easing:"easeOutBack"});
      }
    });

    $('.navbtn').click(function(e){
        e.preventDefault();
        ScrollToContent();
        // $("#content").load('./1_a.html');
        $("#content").load($(this).attr('id')+'.html');
    });

    var scriptString = "You're logged out!";
    $('.SignOut').click(function(){
        $("#controls").fadeOut(300);
        $("#Status").fadeOut(300);
        // $("#controls").hide();
        // $("#controls").load("./loginRGDX.php");
        $.ajax({
          method: 'get',
          url: 'kickout.php',
          data: {
            'myString': scriptString,
            'ajax': true
          },
          success: function(data) {
            $('#controls').load("./loginRGDX.html");
            $("#controls").fadeIn(300);
            // $('#controls').text(data);
          }
        });
        // $("#Status").fadeOut(200);
        // $("#TaskHolder").fadeOut(200);
        // $("#content").fadeOut(200);
        // $("#controls").load("./loginRGDX.php");
        $('.StatusInfo').load('./getTime.php');
        $("#controls").animate({height:'130px'},"fast");
        // $("#controls").show();
    });


    // $('#controls').hover(function() {
    $('.LevelTab2')
    .hover(function() {
        $( '.LevelTab2' ).animate({
            // width: "70%",
            // opacity: 0.4,
            height: "40px",
            width: "130px",
            // marginBo: "-0.2in",
            // fontSize: "3em",
            // borderWidth: "10px"
          }, {duration: "fast", easing:"easeOutBack"} );
    // $('.LevelTab').click(function(){
        // $( this).animate({left:'50'},"slow");
      // $( this ).fadeOut( 100 );
      // $( this ).fadeIn( 500 );
    })
    .mouseleave(function() {
        $( '.LevelTab2' ).animate({
            // width: "70%",
            // opacity: 0.7,
            // marginTop: "0in",
            height: "30px",
            // fontSize: "3em",
            // borderWidth: "10px"
          }, {duration: "fast", easing:"easeOutBack"} );
    });

});
// function loadPage(urlToLoad) {
//     $('#main').load(urlToLoad, function () {
//         alert('Load was performed.');
//     });
// }
// <a onclick="loadPage('load.php');" href="javascript:void(0)" class="nav">
