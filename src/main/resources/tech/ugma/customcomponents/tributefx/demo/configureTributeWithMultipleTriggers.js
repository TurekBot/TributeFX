// See the readme at https://github.com/zurb/tribute to see all the configuration options
var tribute = new Tribute({
    // Array of collections to choose from. One collection uses @, the other #
    collection: [
      // First collection (@-triggered)
      {
        // The selectTemplate determines what will be left behind each
        // time a mention is made
        selectTemplate: function (user) {
          var toDisplay = '<span class="mention" style="font-weight:bold;" contenteditable="false">';

          // Leave behind, the user's name
          toDisplay += user.original.value + '</span>';

          return toDisplay;
        },


        // The text that gets placed after a mention.
        // We don't want any suffix, so we put the empty space
        replaceTextSuffix: '5', // NOTE: This doesn't appear to be working

        // The array of objects that the user can select from.
        // It starts out empty, but will be filled by data coming from
        // the Java side (using the append(collectionIndex, newValues, replace) function)
        // A conventional value array looks like this:
        //    values: [
        //               {key: 'Brant Black', value: 'bblack'},
        //               {key: 'Greg Sample', value: 'gsample'}
        //            ]
        // "key" and "value" are the default names and using them will allow tribute
        // to work right out of the box, looking up the key and leaving behind the value upon selection
        //
        // However, more data attributes are welcome. You just need to
        // tell Tribute which data element (or combination of data elements) to lookup
        //    lookup: function (person) {
        //      return person.firstName + ' ' + person.lastName;
        //    }
        // and what to leave behind when someone makes a mention
        //    fillAttr: firstName
        //
        // An example value array might look like this:
        //    values: [
        //             {
        //               firstName: 'Brant',
        //               lastName: 'Black',
        //               email: 'Brant.Black@gmail.com'
        //             },
        //             {
        //               firstName: 'Greg',
        //               lastName: 'Sample',
        //               email: 'Greg.Sample@gmail.com'
        //             },
        //    ]
        values: [],

        // specify whether a space is allowed in the middle of mentions
        allowSpaces: true
      },

      // Second collection (#-triggered)
      {
        // specify whether a space is allowed in the middle of mentions
        allowSpaces: true,

        // The symbol that starts the lookup
        trigger: '#',

        // The function that gets called on select that returns the content to insert
        selectTemplate: function (item) {
          if (this.range.isContentEditable(this.current.element)) {
            return '<span contenteditable="false" style="font-weight:bold;" '
             + 'class="hashtag">'
             + '#' + item.original.value.replace() + '</span>';
          }

          return '#' + item.original.value;
        },

        // function retrieving an array of objects for display
        values: [
          {key: 'RTV', value: 'RTV'},
          {key: 'ReserveForRetail', value: 'ReserveForRetail'},
          {key: 'NeedsAttention', value: 'NeedsAttention'},
          {key: 'VendorContacted', value: 'VendorContacted'},
          {key: 'Resolved', value: 'Resolved'},
        ],

        // The lookup is 'key' by default
        lookup: 'key',

        // The fillAttr is 'value' by default
//        fillAttr: 'value'
      }
    ]
  });

  tribute.attach(document.getElementById('tributable-container'));
