var express = require('express');
var router = express.Router();

router.get('/addToWatchList', function(req, res, next) {
    res.send(`
    <html>
        <body>
            <form action="/watchlist/add" method="post">
                <input type="text" id="currency_id" name="currency_id"/>
                <input type="submit" value="submit"/>
            </form>
        </body>
    </html>
    `)
});

router.get('/removeFromWatchList', function(req, res, next) {
    res.send(`
    <html>
        <body>
            <form action="/watchlist/remove" method="post">
                <input type="text" id="currency_id" name="currency_id"/>
                <input type="submit" value="submit"/>
            </form>
        </body>
    </html>
    `)
});


router.get('/addToPortfolio', function(req, res, next) {
    res.send(`
    <html>
        <body>
            <form action="/portfolio/add" method="post">
                <label>currency_id</label>
                <input type="text" id="currency_id" name="currency_id"/>
                <br/>
                <label>open_value</label>
                <input type="text" id="open_value" name="open_value"/>
                <br/>
                <label>invested</label>
                <input type="text" id="invested" name="invested"/>
                <br/>
                <input type="radio" name="method" value="buy"/>buy
                <input type="radio" name="method" value="sell"/>sell
                <input type="submit" value="submit"/>
            </form>
        </body>
    </html>
    `)
});

module.exports = router;
