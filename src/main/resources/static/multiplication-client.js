function updateMultiplication() {
    $.ajax({

        url: "http://localhost:8080/multiplications/random"

    }).then(function(data) {

        // Cleans the form
        $("#attempt-form").find( "input[name='multiplicationResult']" ).val("");
        $("#attempt-form").find( "input[name='userLogin']" ).val("");

        // Gets a random challenge from API and loads the data in the HTML
        $('.leftMultiplier').empty().append(data.leftMultiplier);
        $('.rightMultiplier').empty().append(data.rightMultiplier);

    });
}


function updateCountOfTries(userLogin) {
    $.ajax({

        url: "http://localhost:8080/results?login=" + userLogin,

    }).then(function(data){

        $('#tries-body').empty();

        data.forEach(function(row)) {

            $('#tries-body').append('<tr><td>' + row.id +'</td>' +
                    '<td>' + row.multiplication.leftMultiplier + 'x' + row.multiplication.rightMultiplier + '</td>' +
                    '<td>' + row.multiplicationResult + '</td>' +
                    '<td>' + (row.attempt == true? 'SUCCESS': 'FAIL') + '</td></tr>');

            )
        }

    })
}

$(document).ready(function() {

    updateMultiplication();

    $("#attempt-form").submit(function( event ) {

        // Don't submit the form normally
        event.preventDefault();

        // Get some values from elements on the page
        var a = $('.leftMultiplier').text();
        var b = $('.rightMultiplier').text();
        var $form = $( this ),
            attempt = $form.find( "input[name='multiplicationResult']" ).val(),
            userLogin = $form.find( "input[name='userLogin']" ).val();

        // Compose the data in the format that the API is expecting
        var data = { user: { login: userLogin}, multiplication: {leftMultiplier: a, rightMultiplier: b}, multiplicationResult: attempt};

        // Send the data using post
        $.ajax({
            url: '/results',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(result){

                if(result.rightResult) {
                    $('.result-message').empty().append("The result is correct, ").append(userLogin).append("!");
                } else {
                    $('.result-message').empty().append("The result isn't correct, ").append(userLogin).append("!");
                }

            }
        });

        updateMultiplication();
        updateCountOfTries(userLogin);
    });
});

