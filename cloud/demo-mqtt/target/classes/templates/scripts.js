        function timedRefresh(time) {
                setTimeout(() => {
                location.reload(true);
                }, time)
        }
        (() => {
            timedRefresh(1000);
            setTimeout(() => {      }, 50)
        })();