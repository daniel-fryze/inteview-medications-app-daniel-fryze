'use strict';

var browserify = require('browserify'),
    del        = require('del'),
    glob       = require('glob'),
    gulp       = require('gulp'),
    jshint     = require('gulp-jshint'),
    Server     = require('karma').Server,
    source     = require('vinyl-source-stream'),
    vinylPaths = require('vinyl-paths');

// Load all gulp plugins listed in package.json
var gulpPlugins = require('gulp-load-plugins')({
	pattern: ['gulp-*', 'gulp.*'],
	replaceString: /\bgulp[\-.]/
});

// Define file path variables
var paths = {
	root: 'app/',      // App root path
	src:  'app/js/',   // Source path
	dist: 'app/dist/', // Distribution path
	test: 'test/',     // Test path
};

var liveReload = false;

gulp.task('clean', function () {
	return gulp
		.src([paths.root + 'ngAnnotate', paths.dist], {read: false})
		.pipe(vinylPaths(del));
});

gulp.task('lint', function () {
	return gulp
	.src(['gulpfile.js',
		paths.src + '**/*.js',
		paths.test + '**/*.js',
		'!' + paths.src + 'third-party/**',
		'!' + paths.test + 'browserified/**',
	])
	.pipe(gulpPlugins.eslint())
	.pipe(gulpPlugins.eslint.format());
});

gulp.task('linter', function() {
  gulp.src(['./app/**/*.js', '!./app/bower_components/**'])
    .pipe(jshint())
    .pipe(jshint.reporter('default'))
    .pipe(jshint.reporter('fail'));
});

gulp.task('unit', function () {
	return gulp.src([
		paths.test + 'unit/**/*.js'
	])
	.pipe(gulpPlugins.mocha({reporter: 'dot'}));
});

gulp.task('browserify', /*['lint', 'unit'],*/ function () {
	return browserify(paths.src + 'app.js', {debug: true})
	.bundle()
	.pipe(source('app.js'))
	.pipe(gulp.dest(paths.dist))
	.pipe(gulpPlugins.connect.reload());
});

gulp.task('ngAnnotate', ['lint', 'unit'], function () {
	return gulp.src([
		paths.src + '**/*.js',
		'!' + paths.src + 'third-party/**',
	])
	.pipe(gulpPlugins.ngAnnotate())
	.pipe(gulp.dest(paths.root + 'ngAnnotate'));
});

gulp.task('browserify-min', ['ngAnnotate'], function () {
	return browserify(paths.root + 'ngAnnotate/app.js')
	.bundle()
	.pipe(source('app.min.js'))
	.pipe(gulpPlugins.streamify(gulpPlugins.uglify({mangle: false})))
	.pipe(gulp.dest(paths.dist));
});

gulp.task('browserify-tests', function () {
	var bundler = browserify({debug: true});
	glob
	.sync(paths.test + 'unit/**/*.js')
	.forEach(function (file) {
		bundler.add(file);
	});
	return bundler
	.bundle()
	.pipe(source('browserified_tests.js'))
	.pipe(gulp.dest(paths.test + 'browserified'));
});

gulp.task('karma', ['browserify-tests'], function (done) {
	new Server({
		configFile: __dirname + '/karma.conf.js',
		singleRun: true
	}, done).start();
});

gulp.task('server', ['browserify'], function () {
	gulpPlugins.connect.server({
		root: 'app',
		livereload: liveReload,
		port: 8008,
	});
});

gulp.task('e2e', ['server'], function () {
	return gulp.src([paths.test + 'e2e/**/*.js'])
	.pipe(gulpPlugins.protractor.protractor({
		configFile: 'protractor.conf.js',
		args: ['--baseUrl', 'http://127.0.0.1:8009'],
	}))
	.on('error', function (e) {  
		throw e;
	})
	.on('end', function () {
		gulpPlugins.connect.serverClose();
	});
});

gulp.task('watch', function () {
	gulp.start('server');
	gulp.watch(
	[ 
		paths.src + '**/*.js',
		'!' + paths.src + 'third-party/**',
		paths.test + '**/*.js', ], 
	['fast']);
});

gulp.task('fast', ['clean'], function () {
	gulp.start('browserify');
});

gulp.task('default', ['clean'], function () {
	liveReload = false;
	gulp.start('karma', 'browserify', 'browserify-min', 'e2e');
});