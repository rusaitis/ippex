/*
 * timeout-dialog.js v1.0.2, 01-03-2012
 *
 * @author: Rodrigo Neri (@rigoneri)
 * @contributor: Jason Ogaard (@jasonogaard)
 *
 * (The MIT License)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/* String formatting, you might want to remove this if you already use it.
 * Example:
 *
 * var location = 'World';
 * alert('Hello {0}'.format(location));
 */
String.prototype.format = function() {
  var s = this, i = arguments.length;

  while (i--) {
    s = s.replace(new RegExp('\\{' + i + '\\}', 'gm'), arguments[i]);
  }
  return s;
};

!function($) {
  $.timeoutDialog = function(options) {

    var settings = {
      timeout : 1,
      countdown : 15,
      title : 'Welcome to RGDX!',
      message : 'Please choose your identity within {0} seconds.',
      question: "",
      keep_alive_button_text: 'Human',
      sign_out_button_text: 'Droid',
      keep_alive_url : '/keep-alive',
      logout_url : null,
      logout_redirect_url : '/',
      restart_on_yes : false,
      dialog_width : 375,
      display_minutes_and_seconds : false
    }

    $.extend(settings, options);

    var TimeoutDialog = {
      init : function() {
        this.setupDialogTimer();
      },

      setupDialogTimer : function() {
        var self = this;
        window.setTimeout(function() {
          self.setupDialog();
        }, (settings.timeout - settings.countdown) * 1000);
      },

      setupDialog : function() {
        var self = this;
        self.destroyDialog();

        var initialTimeDisplay = settings.countdown;
        if(settings.display_minutes_and_seconds) {
          seconds = settings.countdown%60;
          if(seconds === 0) {
            seconds = "00";
          }
          initialTimeDisplay = Math.floor(settings.countdown/60) + ":" + seconds;
        }

        $(
            '<div id="timeout-dialog">'
                + '<p id="timeout-message">'
                + settings.message
                    .format('<span id="timeout-countdown">'
                        + initialTimeDisplay
                        + '</span>') + '</p>'
                + '<p id="timeout-question">'
                + settings.question + '</p>' + '</div>')
            .appendTo('body').dialog({
              modal : true,
              width : settings.dialog_width,
              minHeight : 'auto',
              zIndex : 10000,
              closeOnEscape : false,
              draggable : false,
              resizable : false,
              dialogClass : 'timeout-dialog',
              title : settings.title,
              buttons : {
                'keep-alive-button' : {
                  text : settings.keep_alive_button_text,
                  id : "timeout-keep-signin-btn",
                  click : function() {
                    self.keepAlive();
                  }
                },
                'sign-out-button' : {
                  text : settings.sign_out_button_text,
                  id : "timeout-sign-out-button",
                  click : function() {
                    self.signOut(true);
                  }
                }
              }
            });

        self.startCountdown();
      },

      minutes : function() {
        Math.floor(settings.countdown/60);
      },

      destroyDialog : function() {
        if ($("#timeout-dialog").length) {
          $("#timeout-dialog").dialog("close");
          $('#timeout-dialog').remove();
        }
      },

      startCountdown : function() {
        var self = this,
        counter = settings.countdown;

        this.countdown = window.setInterval(function() {
          counter -= 1;

          if(settings.display_minutes_and_seconds) {
            displayMinutes = Math.floor(counter/60),
            displaySeconds = counter%60;
            if(displaySeconds < 10) {
              displaySeconds = "0" + displaySeconds;
            }
            $("#timeout-countdown").html(displayMinutes + ":" + displaySeconds);
          }
          else {
            $("#timeout-countdown").html(counter);
          }


          if (counter <= 0) {
            window.clearInterval(self.countdown);
            self.signOut(false);
          }

        }, 1000);
      },

      keepAlive : function() {
        var self = this;
        this.destroyDialog();
        window.clearInterval(this.countdown);
        $("#Status").fadeIn(200);
        $("#controls").fadeIn(200);
        $("#controls").animate({height:'520px'},"slow");
        $("#controls").load("./controls.html");
        // $.get(settings.keep_alive_url, function(data) {
        //   if (data == "OK") {
        //     if (settings.restart_on_yes) {
        //       self.setupDialogTimer();
        //     }
        //   } else {
        //     self.signOut(false);
        //   }
        // });
      },

      signOut : function(is_forced) {
        var self = this;
        this.destroyDialog();

        if (settings.logout_url != null) {
          $.post(settings.logout_url, function(data) {
            self.redirectLogout(is_forced);
          });
        } else {
          self.redirectLogout(is_forced);
        }
      },

      redirectLogout : function(is_forced) {
        // var target = settings.logout_redirect_url
        //     + '?next='
        //     + encodeURIComponent(window.location.pathname
        //         + window.location.search);
        // if (!is_forced)
        //   target += '&timeout=t';
        // window.location = target;
        window.clearInterval(this.countdown);
        $("#controls").fadeIn(200);
        $('.timewarning').load('./getTime.php');
        // $("#controls").animate({height:'130px'},"slow");
        $("#controls").load("./loginRGDX.php");
      }
    };

    TimeoutDialog.init();
  };
}(window.jQuery);