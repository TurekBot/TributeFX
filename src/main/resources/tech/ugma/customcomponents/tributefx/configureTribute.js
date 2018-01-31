var tribute = new Tribute({
  values: [
//    {key: 'Phil Heartman', value: 'pheartman'},
//    {key: 'Gordon Ramsey', value: 'gramsey'}
    {key: 'Meg Hebel'},
    {key: 'Katy Reynoso'},
    {key: 'Ladawn Krajewski'},
    {key: 'Clayton Mundt'},
    {key: 'Loyce Clairmont'},
    {key: 'Laquanda Carty'},
    {key: 'Moriah Moir'},
    {key: 'Dillon Bullington'},
    {key: 'Justina Vosburgh'},
    {key: 'Dennis Claborn'},
    {key: 'Raul Lukach'},
    {key: 'Marnie Burnside'},
    {key: 'Deidre Turman'},
    {key: 'Casie Berthelot'},
    {key: 'Delphia Filiault'},
    {key: 'Edyth Friley'},
    {key: 'Dayna Catalano'},
    {key: 'Jolyn Rutten'},
    {key: 'Sharice Maskell'},
    {key: 'Adelia Dobos'},
    {key: 'Bessie Corr'},
    {key: 'Roselia Modlin'},
    {key: 'Samira Serrato'},
    {key: 'Lakita Stayton'},
    {key: 'Ja Hao'},
    {key: 'Libby Fitz'},
    {key: 'Catherine Zabel'},
    {key: 'Beryl Satterfield'},
    {key: 'Melodie Cobb'},
    {key: 'Leana Hulin'},
    {key: 'Rowena Bowling'},
    {key: 'Pasty Gendron'},
    {key: 'Myrna Bottorff'},
    {key: 'Winnie Rentfro'},
    {key: 'Heidi Erion'},
    {key: 'Linh Kisner'},
    {key: 'Carolann Donlin'},
    {key: 'Sylvie Verner'},
    {key: 'Julio Restrepo'},
    {key: 'Sanford Thornell'},
    {key: 'Tonisha Jarrell'},
    {key: 'Dorothy Whipple'},
    {key: 'Joeann Whited'},
    {key: 'Ines Maeda'},
    {key: 'Dayna Stockman'},
    {key: 'Clayton Dotts'},
    {key: 'Harrison Dillingham'},
    {key: 'Sherly Guebert'},
    {key: 'Trey Buie'},
    {key: 'Angelic Bashir'},
    {key: 'Shani Boggs'},
    {key: 'Frederica Larock'},
    {key: 'Amberly Bodine'},
    {key: 'Malik Kemble'},
    {key: 'Miesha Mealy'},
    {key: 'Galen Bonilla'},
    {key: 'Reynalda Ownbey'},
    {key: 'Sau Gettys'},
    {key: 'Jeri Cull'},
    {key: 'Lavonda Denis'},
    {key: 'Dwain Moyers'},
    {key: 'Junko Hedin'},
    {key: 'Mayra Stillson'},
    {key: 'Elvis Stumbaugh'},
    {key: 'Christy Faries'},
    {key: 'Margarite Severson'},
    {key: 'Shanika Tseng'},
    {key: 'Jose Earle'},
    {key: 'Haywood Atha'},
    {key: 'Emmanuel Jungers'},
    {key: 'Aurora Cope'},
    {key: 'Terresa Stubbe'},
    {key: 'Sid Geraci'},
    {key: 'Boris Winnett'},
    {key: 'Michael Erlandson'},
    {key: 'Georgette Cusick'},
    {key: 'Bethanie Samet'},
    {key: 'Susannah Carlino'},
    {key: 'Matha Hanke'},
    {key: 'Micha Rackham'},
    {key: 'Season Fabre'},
    {key: 'Stanford Senger'},
    {key: 'Bernita Gorham'},
    {key: 'Robbyn Hansford'},
    {key: 'Shery Boettcher'},
    {key: 'Jeannie Dillon'},
    {key: 'Clayton Erne'},
    {key: 'Christena Naber'},
    {key: 'Lesley Michaux'},
    {key: 'Dannie Danielsen'},
    {key: 'Mafalda Kimberling'},
    {key: 'Veronica Rinaldi'},
    {key: 'Jae Hillier'},
    {key: 'Meagan Fouse'},
    {key: 'Leatha Grantham'},
    {key: 'Rebecka Weeden'},
    {key: 'Alvaro Carbonneau'},
    {key: 'Danica Backus'},
    {key: 'Joseph Oehler'},
    {key: 'Kitty Ledesma'},
    {key: 'Phillis Nicolosi'},
    {key: 'Janna Symons'},
    {key: 'Elton Rhyne'},
    {key: 'Deja Ciampa'},
    {key: 'Karolyn Eanes'},
    {key: 'Lessie Graber'},
    {key: 'Teresa Bowen'},
    {key: 'Tuan Rogerson'},
    {key: 'Patrica Najarro'},
    {key: 'Bethel Bolender'},
    {key: 'Sona Bond'},
    {key: 'Curt Koller'},
    {key: 'Lawrence Levens'},
    {key: 'Marietta Meisel'},
    {key: 'Thu Gula'},
    {key: 'Venita Jaquez'},
    {key: 'Terri Hames'},
    {key: 'Reynalda Brindley'},
    {key: 'Beverlee Manier'},
    {key: 'Judson Lehto'},
    {key: 'Lorie Daw'},
    {key: 'Noreen Hurdle'},
    {key: 'Tiffani Rutkowski'},
    {key: 'Zenaida Lawver'},
    {key: 'Aurelia Lant'},
    {key: 'Estela Huntoon'},
    {key: 'Farrah Bogart'},
    {key: 'Nisha Dykes'},
    {key: 'Inell Ridings'},
    {key: 'Lennie Cabe'}
  ],
  fillAttr: 'key',
  replaceTextSuffix: '',
  allowSpaces: true
});

tribute.attach(document.getElementById('tributable-container'));

////See the readme at https://github.com/zurb/tribute to see all the configuration options
//
//var tributeMultipleTriggers = new Tribute({
//  //Array of collections to choose from. One collection uses @, the other #
//  collection: [
//    //First collection (@-triggered)
//    {
//
//
//      //The selectTemplate determines what will be left behind each
//      // time a mention is made
//      selectTemplate: function (person) {
//        var toDisplay = '<span class="mention" contenteditable="false" ';
//
//        //Get user's email and hide it in the span
//        toDisplay += 'email="' + person.original.email + '">';
//
//        //Leave behind, just the person's first name (you know, cuz that's cool.)
//        toDisplay += '@' + person.original.firstName + '</span>';
//
//        return toDisplay;
//      },
//
//
//      //The text that gets placed after a mention.
//      //We don't want any suffix, so we put the empty space
//      replaceTextSuffix: '5',
//
//      // The array of objects that the user can select from.
//      // It starts out empty, but will be filled by data coming from
//      // the Java side (using the append(collectionIndex, newValues, replace) function)
//      // A conventional value array looks like this:
//      //    values: [
//      //               {key: 'Brant Black', value: 'bblack'},
//      //               {key: 'Greg Sample', value: 'gsample'}
//      //            ]
//      // "key" and "value" are the default names and using them will allow tribute
//      // to work right out of the box, looking up the key and leaving behind the value upon selection
//      //
//      // However, more data attributes are welcome. You just need to
//      // tell Tribute which data element (or combination of data elements) to lookup
//      //    lookup: function (person) {
//      //      return person.firstName + ' ' + person.lastName;
//      //    }
//      // and what to leave behind when someone makes a mention
//      //    fillAttr: firstName
//      //
//      // An example value array might look like this:
//      //    values: [
//      //             {
//      //               firstName: 'Brant',
//      //               lastName: 'Black',
//      //               email: 'Brant.Black@gmail.com'
//      //             },
//      //             {
//      //               firstName: 'Greg',
//      //               lastName: 'Sample',
//      //               email: 'Greg.Sample@gmail.com'
//      //             },
//      //    ]
//      values: [
//        {
//          firstName: 'Brant',
//          lastName: 'Black',
//          email: 'Brant.Black@gmail.com'
//        },
//        {
//          firstName: 'Greg',
//          lastName: 'Sample',
//          email: 'Greg.Sample@gmail.com'
//        },
//      ],
//
//      lookup: function (person) {
//        return person.firstName + ' ' + person.lastName;
//      },
//
//      // specify whether a space is allowed in the middle of mentions
//      allowSpaces: true
//    }
//
//    //Second collection (#-triggered)
////    {
////      // specify whether a space is allowed in the middle of mentions
////      allowSpaces: true,
////
////      // The symbol that starts the lookup
////      trigger: '#',
////
////      // The function that gets called on select that returns the content to insert
////      selectTemplate: function (item) {
////        if (this.range.isContentEditable(this.current.element)) {
////          return '<span contenteditable="false" class="hashtag">'
////           + '#' + item.original.hashtag.replace() + '</span>';
////        }
////
////        return '#' + item.original.hashtag;
////      },
////
////      // function retrieving an array of objects for display
////      values: [
////        {hashtag: 'RTV'},
////        {hashtag: 'ReserveForRetail'},
////        {hashtag: 'NeedsAttention'},
////        {hashtag: 'VendorContacted'},
////        {hashtag: 'Resolved'},
////      ],
////
////      lookup: 'hashtag',
////
////      fillAttr: 'hashtag'
////    }
//  ]
//});
//
////Attach the tribute object to an element in the html
//      tributeMultipleTriggers.attach(document.getElementById('tributable-container'));
