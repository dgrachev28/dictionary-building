module.exports = {
    browserify: {
        // Enable source maps
        debug: true,
        // Additional file extensions to make optional
        //extensions: ['.coffee', '.hbs'],
        // A separate bundle will be generated for each
        // bundle config in the list below
        bundleConfigs: {
            entries:    './js/!_*.js',
            dest:       './js/prod/'
        }
    },
    autoprefixer: {
        browsers: [
            'last 2 versions',
            'safari 5',
            'ie 9',
            'opera 12.1',
            'ios 6',
            'android 4'
        ],
        cascade: true
    },
    less: {
        src: 'styles/*.less',
        dest: 'styles/'
    },
    js: {
        src: ['./modules/**/*.js', './plugins/**/*.js'],
        dest: './js/'
    },
    watch: {
        less: 'styles/**/**/**/*.less',
        scripts: ['./modules/**/*.js', './plugins/**/*.js']
    },
    plugins: {
        scope: ['dependencies', 'devDependencies', 'peerDependencies'],
        rename: {
            'gulp-sourcemaps': 'sourcemaps',
            'gulp-plumber': 'plumber',
            'gulp-less': 'less',
            'gulp-autoprefixer': 'autoprefixer',
            'gulp-cssnano': 'minifyCSS',
            'gulp-soynode': 'soy',
            'gulp-image-data-uri': 'uri',
            'gulp-concat': 'concat'
        }
    }
};