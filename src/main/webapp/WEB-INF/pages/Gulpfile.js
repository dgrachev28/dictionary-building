"use strict";

const config     = require('./gulp/config');

var gulp         = require('gulp');
var path         = require("path");
var plugins      = require('gulp-load-plugins')(config.plugins);



/*Less tasks*/
gulp.task('less', function() {
        gulp.src(config.less.src)
            .pipe(plugins.plumber({
                errorHandler: onPlumberError
            }))
            .pipe(plugins.less())
            .pipe(plugins.autoprefixer(config.autoprefixer))
            .pipe(gulp.dest(config.less.dest))
    }
);

//Watchers
gulp.task('watch', function() {
    gulp.watch(config.watch.less, ['less']);
});

gulp.task('watch:single', function() {
    gulp.watch(config.watch.less, ['less']);
});



//General tasks
gulp.task('default', ['watch:single']);





/*HELPERS*/
process.on('uncaughtException', function(err) {
    console.error(err.message, err.stack, err.errors);
    process.exit(255);
});

gulp.on('err', function(gulpErr) {
    if (gulpErr.err) {
        console.error("Gulp error details", [gulpErr.err.message, gulpErr.err.stack, gulpErr.err.errors].filter(Boolean));
    }
});

function onPlumberError(error) {
    console.log(error);
    this.emit('end');
}
