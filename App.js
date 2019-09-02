/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Fragment, Component} from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  Button,
} from 'react-native';

import {
  Header,
  LearnMoreLinks,
  Colors,
  DebugInstructions,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';


function onPressRoute(){
  alert("hello")
}

export default class MainView extends Component {
  render() {
    return (
      <Fragment>
      <View style={{
        flex: 3,
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'stretch',
      }}>
          <View style={{ flex: 3,justifyContent: 'center',backgroundColor: 'blue'}}>
            <Button
              // onPress={onPressRoute()}
              title="Route"
              color="#fff"
              />
          </View>
          <View style={{ flex: 3,justifyContent: 'center',backgroundColor: 'green'}}>
          <Button
              // onPress={onPressRoute()}
              title="Left listener"
              color="#fff"
              />
        </View>
        <View style={{ flex: 3,justifyContent: 'center',backgroundColor: 'yellow'}}>
        <Button
              // onPress={onPressRoute()}
              title="Right listener"
              color="#000"
              />
        </View>
      </View>    
    </Fragment>
    );
  }
}

