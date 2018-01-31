//See the readme at https://github.com/zurb/tribute to see all the configuration options

var tributeMultipleTriggers = new Tribute({
  //Array of collections to choose from. One collection uses @, the other #
  collection: [
    //First collection (@-triggered)
    {


      //The selectTemplate determines what will be left behind each
      // time a mention is made
      selectTemplate: function (person) {
        var toDisplay = '<span class="mention" contenteditable="false" ';

        //Get user's email and hide it in the span
        toDisplay += 'email="' + person.original.email + '">';

        //Leave behind, just the person's first name (you know, cuz that's cool.)
        toDisplay += '@' + person.original.firstName + '</span>';

        return toDisplay;
      },


      //The text that gets placed after a mention.
      //We don't want any suffix, so we put the empty space
      replaceTextSuffix: '5',

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
      values: [
        {
          firstName: 'Brant',
          lastName: 'Black',
          email: 'Brant.Black@gmail.com'
        },
        {
          firstName: 'Greg',
          lastName: 'Sample',
          email: 'Greg.Sample@gmail.com'
        },
      ],

      lookup: function (person) {
        return person.firstName + ' ' + person.lastName;
      },

      // specify whether a space is allowed in the middle of mentions
      allowSpaces: true
    }

    //Second collection (#-triggered)
//    {
//      // specify whether a space is allowed in the middle of mentions
//      allowSpaces: true,
//
//      // The symbol that starts the lookup
//      trigger: '#',
//
//      // The function that gets called on select that returns the content to insert
//      selectTemplate: function (item) {
//        if (this.range.isContentEditable(this.current.element)) {
//          return '<span contenteditable="false" class="hashtag">'
//           + '#' + item.original.hashtag.replace() + '</span>';
//        }
//
//        return '#' + item.original.hashtag;
//      },
//
//      // function retrieving an array of objects for display
//      values: [
//        {hashtag: 'RTV'},
//        {hashtag: 'ReserveForRetail'},
//        {hashtag: 'NeedsAttention'},
//        {hashtag: 'VendorContacted'},
//        {hashtag: 'Resolved'},
//      ],
//
//      lookup: 'hashtag',
//
//      fillAttr: 'hashtag'
//    }
  ]
});

//Attach the tribute object to an element in the html
      tributeMultipleTriggers.attach(document.getElementById('tributable-container'));
