//This text will show until the user focuses the container.
var PROMPT_TEXT = 'To mention someone try, "Hey, @John Sample, can you..."'

//Get the container for later use
var container = document.getElementById('container');

//Removes the prompt text from the container
function removePromptText() {
  var promptText = document.getElementById('prompt-text');

  if (promptText != null) {
    container.removeChild(promptText);
    console.log("Removed prompt text.");
  } else {
    console.log("Tried to remove the prompt text; couldn't find it.")
  }
};

//Adds the prompt text to the container
function addPromptText() {
  if (document.getElementById('prompt-text') == null) {
    console.log('Adding the prompt text');
    var promptText = document.createElement('span');
    promptText.setAttribute('id', 'prompt-text');
    promptText.textContent = PROMPT_TEXT;

    container.appendChild(promptText);
  } else {
    console.log('Prompt Text already present. Not going to add it again.');
  }
};

//Add the prompt text when the body loads
document.body.onload = addPromptText;
//Remove it once the container is in focus.
container.onfocus = removePromptText;
