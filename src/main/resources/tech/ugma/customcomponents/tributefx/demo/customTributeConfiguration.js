/*This is JavaScript*/
var tribute = new Tribute({
    values: [],
    selectTemplate: function(person) {
      return '<span contenteditable="false" class="mention" name="' + person.original.key +
      '" title="' + person.original.key + '">' + this.current.collection.trigger
      + person.original[this.current.collection.fillAttr] + '</span>'
    },
    allowSpaces: true,
    replaceTextSuffix: '',
  });
  tribute.attach(document.getElementById('tributable-container'));

  /*Here's all the options you can mess with. See https://github.com/zurb/tribute#a-collection*/
  /*
  {
    // symbol that starts the lookup
    trigger: '@',

    // element to target for @mentions
    iframe: null,

    // class added in the flyout menu for active item
    selectClass: 'highlight',

    // function called on select that returns the content to insert
    selectTemplate: function (item) {
      return '@' + item.original.value;
    },

    // template for displaying item in menu
    menuItemTemplate: function (item) {
      return item.string;
    },

    // template for when no match is found (optional),
    // If no template is provided, menu is hidden.
    noMatchTemplate: null,

    // specify an alternative parent container for the menu
    menuContainer: document.body,

    // column to search against in the object (accepts function or string)
    lookup: 'key',

    // column that contains the content to insert by default
    fillAttr: 'value',

    // REQUIRED: array of objects to match
    values: [],

    // specify whether a space is required before the trigger character
    requireLeadingSpace: true,

    // specify whether a space is allowed in the middle of mentions
    allowSpaces: false,

    // optionally specify a custom suffix for the replace text
    // (defaults to empty space if undefined)
    replaceTextSuffix: '\n',

    // specify whether the menu should be positioned.  Set to false and use in conjuction with menuContainer to create an inline menu
    // (defaults to true)
    positionMenu: true,
  }
  */