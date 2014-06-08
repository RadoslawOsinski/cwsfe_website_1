jQuery(window).load(function() {

    var refreshMemoryInfo = function () {
        $.ajax({
            url: 'generalMemoryInfo',
            dataType:"json",
            success: function(data) {
                $('#usedMemoryInMb').html(data.usedMemoryInMb);
                $('#availableMemoryInMB').html(data.availableMemoryInMB);
            }
        });
    };
    setInterval(refreshMemoryInfo, 3000);

});