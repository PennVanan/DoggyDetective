// Module dependencies.
var express = require('express');
var logfmt = require("logfmt");
var mongo = require('mongodb');
var monk = require('monk');

var mongoUrl = process.env.MONGOLAB_URI ||
  process.env.MONGOHQ_URL ||
  'mongodb://localhost/cis350';
var db = monk(mongoUrl);

var app = express();
app.use(logfmt.requestLogger());


// Configuration
app.configure( function() {
    app.set('views', __dirname + '/views');
    app.set('view engine', 'jade');
    app.use(express.bodyParser());
    app.use(express.methodOverride());
    app.use(require('stylus').middleware({ src: __dirname + '/public' }));
    app.use(app.router);
    app.use(express.static(__dirname + '/public'));
});

// Routes
app.get('/', function(req, res) {
  var docToCSV = function(doc, maxResults) {
    var csv = '';
    csv += doc.sessionNumber + ',';
    csv += doc.date + ',';
    csv += doc.time + ',';
    csv += doc.handler + ',';
    csv += doc.dog + ',';
    csv += doc.videographer + ',';
    csv += doc.observers + ',';
    csv += doc.experimentalName + ',';
    csv += doc.experimentalSlot + ',';
    var controls = '';
    var key, i;
    for(key in doc.controls) {
      controls += key + ':' + doc.controls[key] + ';';
    }
    csv += controls + ',';
    var results = '';
    for(i=0; i<maxResults; i++) {
      if(i < doc.results.length) {
        for(var j = 0; j < doc.results[i].length; j++) {
          results += 'numFalse:' + doc.results[i][j].numFalse + ';';
          results += 'numMiss:' + doc.results[i][j].numMiss + ';';
          results += 'numSuccess:' + doc.results[i][j].numSuccess + ';';
        }
      }
      results += ',';
      if(i < doc.notes.length) {
        results += doc.notes[i];
      }
      results += ',';
      if(i < doc.topArm.length) {
        results += doc.topArm[i];
      }
      results += ',';
    }
    csv += results.substr(0, results.length - 1) + '\n';
    return csv;
  }
  db.get('sessions').find({}, {}, function(e, docs) {
    var max = 0;
    docs.forEach(function(doc) {
      if(doc.results.length > max) {
        max = doc.results.length;
      }
    });
    var csv = '';
    csv += 'Session Number,Date,Time,Handler,Dog,Videographer,Observers,';
    csv += 'Experimental Name,Experimental Slot,Controls,';
    for(var i = 0; i < max; i++) {
      csv += 'Results[' + i + '],Notes[' + i + '],Top Arm[' + i + ']';
      if(i < max -1) {
        csv += ',';
      }
    }
    csv += '\n';
    docs.forEach(function(doc) {
      csv += docToCSV(doc, max);
    });
    res.set('Content-Type', 'text/csv');
    res.send(csv);
  });
});

app.post('/', function(req, res) {
  var sessions = db.get('sessions');
  var callback = function(e, doc) {
    if(e) {
      res.send("Problem saving the session");
    } else {
      res.redirect('/');
    }
  };
  if(req.body.edit) {
    delete req.body.edit;
    var set = {$set: req.body};
    sessions.update({sessionNumber: req.body.sessionNumber}, set, callback);
  } else {
    if('results' in req.body) {
      sessions.insert(req.body, callback);
    }
  }
});

app.listen(Number(process.env.PORT || 5000), function() {
  console.log("Listening");
});
