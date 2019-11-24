# Views
## View structure
There are three main views implemented in the application:
* Home view: This view allows the user to choose whether the device is going to be the main device presenting the maps and all the functionality or just a vibrating device (left or right) to indicate the directions.
* Main view: This is the maps view where the user can specify the place he wants to navigate to and start using the application.
* Side view: This is a blank view which should put the device in a state where it vibrates to indicate the direction.

## Workflow
As soon as the application starts, it creates a dialog containing all the device configuration choices that the user can choose from, either Main device, Left device or Right device.

After the user chooses, the app switches the current "Activity" to the chosen activity and depending on the choice entered it shows the respective view.

## Classes
Every view in Android development is considered an Activity. We represent each view in our application with a class.
### MainDialog
This class represents the main application dialog view. It manifests the dialog from which the user can choose the configuration of the device running the application.
#### Methods
onCreateDialog
> Creates the dialog view and calls the switching activity methods depending on the user choice.

toSideViewActivity
> Switches the view to the side view if the user chooses the side view configuration.

toMainViewActivity
> Switches the view to the main view if the user chooses the main view configuration.

### MainViewActivity
This class represent the maps view with all the functionality and the corresponding methods.
#### Methods
onCreate
<!-- Add your description -->

onMapReady
<!-- Add your description -->

### SideViewActivity
This class represents either of the side view activites; left and right.
It is just a blank view with a message saying "This will vibrate when input is received" as this acts as a vibrating device.

#### Methods
onCreate
> This method sets the view of the application to the side activity view.

onBackPressed
> This handles the press of the back button while in the application and switches the view back to main application view; the one from which the user can choose the device configuration.