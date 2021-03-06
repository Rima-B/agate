'use strict';

agate.config
  .factory('ConfigurationResource', ['$resource',
  function ($resource) {
    return $resource('ws/config', {}, {
      // override $resource.save method because it uses POST by default
      'save': {method: 'PUT'},
      'get': {method: 'GET'}
    });
  }])
  .factory('PublicConfigurationResource', ['$resource',
    function ($resource) {
      return $resource('ws/config/_public', {}, {
        // override $resource.save method because it uses POST by default
        'get': {method: 'GET'}
      });
    }])
  .factory('KeyStoreResource', ['$resource',
    function ($resource) {
      return $resource('ws/config/keystore/system/https', {}, {
        'save': {method: 'PUT'}
      });
    }])
  .factory('StyleEditorService', [
    function () {
      return {
        /**
         * HACK until angular-ui-ce can config path settings
         */
        configureAcePaths: function () {
          var defaultPath = ace.config.get('basePath');

          if (defaultPath.indexOf('bower_components') === -1) {
            // production path must be changed
            ace.config.set('basePath', '/scripts');
            ace.config.set('modePath', '/scripts');
            ace.config.set('themePath', '/scripts');
            ace.config.set('workerPath', '/scripts');
          }
        },

        getEditorOptions: function () {
          return {
            options: {
              theme: 'monokai',
              mode: 'css',
              displayIndentGuides: true,
              useElasticTabstops: true
            }
          };
        }
      };
    }]);
