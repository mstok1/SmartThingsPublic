/**
 *  Zooz Zen27 Dimmer Switch
 *
 * Revision History:
 * 2018-10-27 - Initial release
 * 2018-11-18 - Fixes for parameters due to platform changes
 * 2018-11-19 - Fix LED parameter for all values
 * 2019-02-16 - Added association
 * 2019-07-13 - Fix logic for null preferences on power restore
 * 2019-09-07 - Fix typo in auto turn off timer parameter setting
 *
 *  Supported Command Classes
 *         Association v2
 *         Association Group Information
 *         Basic
 *         Configuration
 *         Device Reset Local
 *         Manufacturer Specific v2
 *         Powerlevel
 *         Switch_all
 *         Switch_multilevel
 *         Version v2
 *         ZWavePlus Info v2
 
 * COMMAND_CLASS_ASSOCIATION
 * COMMAND_CLASS_ASSOCIATION_GRP_INFO
 * COMMAND_CLASS_CONFIGURATION
 * COMMAND_CLASS_DEVICE_RESET_LOCALLY
 * COMMAND_CLASS_MANUFACTURER_SPECIFIC
 * COMMAND_CLASS_POWERLEVEL
 * COMMAND_CLASS_SWITCH_MULTILEVEL
 * COMMAND_CLASS_VERSION
 * COMMAND_CLASS_ZWAVEPLUS_INFO
 * COMMAND_CLASS_MULTI_CHANNEL_ASSOCIATION
 * COMMAND_CLASS_TRANSPORT_SERVICE
 * COMMAND_CLASS_SECURITY_2
 * COMMAND_CLASS_SUPERVISION
 * COMMAND_CLASS_FIRMWARE_UPDATE_MD
 *  
 *   Parm Size Description                                   Value
 *      1    1 Invert Switch                                 0 (Default)-Upper paddle turns light on, 1-Lower paddle turns light on
 *      2    1 LED Indicator                                 0 (Default)-LED is on when light is OFF, 1-LED is on when light is ON, 2-LED is always off, 3-LED is always on
 *      3    1 Auto Turn-Off                                 0 (Default)-Timer disabled, 1-Timer enabled; Set time in parameter 4
 *      4    4 Turn-off Timer                                60 (Default)-Time in minutes after turning on to automatically turn off (1-65535 minutes)
 *      5    1 Auto Turn-On                                  0 (Default)-Timer disabled, 1-Timer enabled; Set time in parameter 6
 *      6    4 Turn-on Timer                                 60 (Default)-Time in minutes after turning off to automatically turn on (1-65535 minutes)
 *      8    1 Power Restore                                 2 (Default)-Remember state from pre-power failure, 0-Off after power restored, 1-On after power restore
 *      9    1 Ramp Rate Control                             1 (Default)-Ramp rate in seconds to reach full brightness or off (1-99 seconds)
 *     10    1 Minimum Brightness                            1 (Default)-Minimum brightness that light will set (1-99%)
 *     11    1 Maximum Brightness                            99 (Default)-Maximum brightness that light will set (1-99%)
 *     12    1 Double Tap                                    0 (Default)-Light will go to full brightness with double tap, 1-light will go to max set in Parameter 11 with double tap 
 */

metadata {
	definition (name: "Zooz Zen27 Dimmer", namespace: "doncaruana", author: "Don Caruana", ocfDeviceType: "oic.d.light", mnmn: "SmartThings", vid: "generic-dimmer") {
		capability "Switch Level"
		capability "Actuator"
		capability "Health Check"
		capability "Switch"
		capability "Polling"
		capability "Refresh"
		capability "Sensor"
		capability "Light"

 //zw:L type:1101 mfr:027A prod:A000 model:A002 ver:1.00 zwv:5.03 lib:03 cc:5E,26,85,8E,59,55,86,72,5A,73,70,9F,6C,7A role:05 ff:8600 ui:8604

	fingerprint mfr:"027A", prod:"A000", model:"A002", ver:"1.00", deviceJoinName: "Zooz Zen27 Dimmer"
	fingerprint deviceId:"0x1101", inClusters: "0x26,0x55,0x59,0x5A,0x5E,0x6C,0x70,0x72,0x73,0x7A,0x85,0x86,0x8E,0x9F"
	fingerprint cc: "0x26,0x55,0x59,0x5A,0x5E,0x6C,0x70,0x72,0x73,0x7A,0x85,0x86,0x8E,0x9F", mfr:"027A", prod:"A000", model:"A002", ver:"1.00", deviceJoinName: "Zooz Zen27 Dimmer"

	}

	simulator {
		status "on":  "command: 2003, payload: FF"
		status "off": "command: 2003, payload: 00"
		status "09%": "command: 2003, payload: 09"
		status "10%": "command: 2003, payload: 0A"
		status "33%": "command: 2003, payload: 21"
		status "66%": "command: 2003, payload: 42"
		status "99%": "command: 2003, payload: 63"

		// reply messages
		reply "2001FF,delay 5000,2602": "command: 2603, payload: FF"
		reply "200100,delay 5000,2602": "command: 2603, payload: 00"
		reply "200119,delay 5000,2602": "command: 2603, payload: 19"
		reply "200132,delay 5000,2602": "command: 2603, payload: 32"
		reply "20014B,delay 5000,2602": "command: 2603, payload: 4B"
		reply "200163,delay 5000,2602": "command: 2603, payload: 63"
	}

	preferences {
		input "ledIndicator", "enum", title: "LED Indicator", description: "When Off... ", options:["on": "When On", "off": "When Off", "never": "Never", "always": "Always"], defaultValue: "off"
		input "invertSwitch", "bool", title: "Invert Switch", description: "Flip switch upside down", required: false, defaultValue: false
		input "rampRate", "number", title: "Ramp Rate", description: "Seconds to reach full brightness (1-99)", required: false, defaultValue: 1, range: "1..99"
		input "powerRestore", "enum", title: "After Power Restore", description: "State after power restore", options:["prremember": "Remember", "proff": "Off", "pron": "On"],defaultValue: "prremember",displayDuringSetup: false
		input "doubleTap", "bool", title: "Double Tap", description: "Double Tap limited to max set", required: false, defaultValue: false
		input "autoTurnoff", "bool", title: "Auto Off", description: "Light will automatically turn off after set time", required: false, defaultValue: false
		input "autoTurnon", "bool", title: "Auto On", description: "Light will automatically turn on after set time", required: false, defaultValue: false
		input "offTimer", "number", title: "Off Timer", description: "Time in minutes to automatically turn off", required: false, defaultValue: 60, range: "1..65535"
		input "onTimer", "number", title: "On Timer", description: "Time in minutes to automatically turn on", required: false, defaultValue: 60, range: "1..65535"
		input "maxBright", "number", title: "Maximum Brightness", description: "Maximum brightness that the light can go to", required: false, defaultValue: 99, range: "1..99"
		input "minBright", "number", title: "Minimum Brightness", description: "Minimum brightness that the light can go to", required: false, defaultValue: 1, range: "1..99"
		input (
					type: "paragraph",
					element: "paragraph",
					title: "Configure Association Groups:",
					description: "Devices in association group 2 will receive Basic Set commands directly from the switch when it is turned on or off. Use this to control another device as if it was connected to this switch.\n\n" +"Devices are entered as a comma delimited list of IDs in hexadecimal format."
					)
		input (
					name: "requestedGroup2",
					title: "Association Group 2 Members (Max of 5):",
					type: "text",
					required: false
					)
	}

	tiles(scale: 2) {
		multiAttributeTile(name:"switch", type: "lighting", width: 6, height: 4, canChangeIcon: true){
			tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
				attributeState "on", label:'${name}', action:"switch.off", icon:"st.switches.switch.on", backgroundColor:"#00a0dc", nextState:"turningOff"
				attributeState "off", label:'${name}', action:"switch.on", icon:"st.switches.switch.off", backgroundColor:"#ffffff", nextState:"turningOn"
				attributeState "turningOn", label:'${name}', action:"switch.off", icon:"st.switches.switch.on", backgroundColor:"#00a0dc", nextState:"turningOff"
				attributeState "turningOff", label:'${name}', action:"switch.on", icon:"st.switches.switch.off", backgroundColor:"#ffffff", nextState:"turningOn"
			}
			tileAttribute ("device.level", key: "SLIDER_CONTROL") {
				attributeState "level", action:"switch level.setLevel"
			}
		}

		standardTile("refresh", "device.switch", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
			state "default", label:'', action:"refresh.refresh", icon:"st.secondary.refresh"
		}

		valueTile("level", "device.level", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
			state "level", label:'${currentValue} %', unit:"%", backgroundColor:"#ffffff"
		}

		main(["switch"])
		details(["switch", "level", "refresh"])

	}
}

def installed() {
	log.debug "installed"
	def cmds = []
// Device-Watch simply pings if no device events received for 32min(checkInterval)
	sendEvent(name: "checkInterval", value: 2 * 15 * 60 + 2 * 60, displayed: false, data: [protocol: "zwave", hubHardwareId: device.hub.hardwareID])
	cmds << mfrGet()
	cmds << zwave.versionV1.versionGet().format()
	cmds << parmGet(1)
	cmds << parmGet(2)
	cmds << parmGet(3)
	cmds << parmGet(4)
	cmds << parmGet(5)
	cmds << parmGet(6)
	cmds << parmGet(8)
	cmds << parmGet(9)
	cmds << parmGet(10)
	cmds << parmGet(11)
	cmds << parmGet(12)

	def level = 99
	cmds << zwave.basicV1.basicSet(value: level).format()
    cmds << zwave.switchMultilevelV1.switchMultilevelGet().format()
	return response(delayBetween(cmds,200))
}

def updated(){
	// These are needed when parameter defaults are null or non-workable numbers. They are set to the device defaults
	def setOffTimer = 60
	if (offTimer) {setOffTimer = offTimer}
	def setOnTimer = 60
	if (onTimer) {setOnTimer = onTimer}
	def setRampRate = 1
	if (rampRate) {setRampRate = rampRate}
	def setMaxBright = 99
	if (maxBright) {setMaxBright = maxBright}
	def setMinBright = 1
	if (minBright) {setMinBright = minBright}
	def nodes = []
	def commands = []
	if (getDataValue("MSR") == null) {
		def level = 99
		commands << mfrGet()
		commands << zwave.versionV1.versionGet().format()
		commands << zwave.basicV1.basicSet(value: level).format()
		commands << zwave.switchMultilevelV1.switchMultilevelGet().format()
	}
	def setDoubleTap = doubleTap == true ? 1 : 0
	def setPowerRestore = 2
	if (powerRestore != null) {setPowerRestore = powerRestore == "prremember" ? 2 : powerRestore == "proff" ? 0 : 1}
	def setAutoTurnon = autoTurnon == true ? 1 : 0
	def setAutoTurnoff = autoTurnoff == true ? 1 : 0
	def setInvertSwitch = invertSwitch == true ? 1 : 0
	def setLedIndicator = 0
	switch (ledIndicator) {
		case "off":
			setLedIndicator = 0
			break
		case "on":
			setLedIndicator = 1
			break
		case "never":
			setLedIndicator = 2
			break
		case "always":
			setLedIndicator = 3
			break
		default:
			setLedIndicator = 0
			break
	}
	
	if (settings.requestedGroup2 != state.currentGroup2) {
		nodes = parseAssocGroupList(settings.requestedGroup2, 2)
		commands << zwave.associationV2.associationRemove(groupingIdentifier: 2, nodeId: []).format()
		commands << zwave.associationV2.associationSet(groupingIdentifier: 2, nodeId: nodes).format()
		commands << zwave.associationV2.associationGet(groupingIdentifier: 2).format()
		state.currentGroup2 = settings.requestedGroup2
	}
	
	//parmset takes the parameter number, it's size, and the value - in that order
	commands << parmSet(12, 1, setDoubleTap)
	commands << parmSet(11, 1, setMaxBright)
	commands << parmSet(10, 1, setMinBright)
	commands << parmSet(9, 1, setRampRate)
	commands << parmSet(8, 1, setPowerRestore)
	commands << parmSet(6, 4, setOnTimer)
	commands << parmSet(5, 1, setAutoTurnon)
	commands << parmSet(4, 4, setOffTimer)
	commands << parmSet(3, 1, setAutoTurnoff)
	commands << parmSet(2, 1, setLedIndicator)
	commands << parmSet(1, 1, setInvertSwitch)

	commands << parmGet(12)
	commands << parmGet(11)
	commands << parmGet(10)
	commands << parmGet(9)
	commands << parmGet(8)
	commands << parmGet(6)
	commands << parmGet(5)
	commands << parmGet(4)
	commands << parmGet(3)
	commands << parmGet(2)
	commands << parmGet(1)
	// Device-Watch simply pings if no device events received for 32min(checkInterval)
	sendEvent(name: "checkInterval", value: 2 * 15 * 60 + 2 * 60, displayed: false, data: [protocol: "zwave", hubHardwareId: device.hub.hardwareID])
	return response(delayBetween(commands, 500))
}

private getCommandClassVersions() {
	[
		0x59: 1,  // AssociationGrpInfo
		0x85: 2,  // Association
		0x5A: 1,  // DeviceResetLocally
		0x72: 2,  // ManufacturerSpecific
		0x73: 1,  // Powerlevel
		0x86: 1,  // Version
		0x5E: 2,  // ZwaveplusInfo
		0x26: 1,  // Multilevel Switch
		0x70: 1,  // Configuration
		0x55: 1,  //Transport Service
		0x6C: 1,  //Supervision
		0x7A: 1,  //Firmware Update Metadata
		0x8E: 1,  //Multi Channel Association

		0x20: 1,  // Basic
		0x27: 1,  // All Switch
//0x9F - what is this class?
	]
}

def parse(String description) {
	def result = null
	// UI Trick to make 99%, which is actually the platform limit, show 100% on the control
	if (description.indexOf('command: 2603, payload: 63 63 00') > -1) {
		description = description.replaceAll('payload: 63 63 00','payload: 64 64 00')
	}
	if (description != "updated") {
		log.debug "parse() >> zwave.parse($description)"
		def cmd = zwave.parse(description, commandClassVersions)
		if (cmd) {
			result = zwaveEvent(cmd)
		}
	}
	if (result?.name == 'hail' && hubFirmwareLessThan("000.011.00602")) {
		result = [result, response(zwave.basicV1.basicGet())]
		log.debug "Was hailed: requesting state update"
	} else {
		log.debug "Parse returned ${result?.descriptionText}"
	}
	return result
}

def zwaveEvent(physicalgraph.zwave.commands.basicv1.BasicReport cmd) {
	dimmerEvents(cmd)
}

def zwaveEvent(physicalgraph.zwave.commands.basicv1.BasicSet cmd) {
	dimmerEvents(cmd)
}

def zwaveEvent(physicalgraph.zwave.commands.associationv2.AssociationReport cmd) {
	log.debug "---ASSOCIATION REPORT V2--- ${device.displayName} sent groupingIdentifier: ${cmd.groupingIdentifier} maxNodesSupported: ${cmd.maxNodesSupported} nodeId: ${cmd.nodeId} reportsToFollow: ${cmd.reportsToFollow}"
	state.group3 = "1,2"
	if (cmd.groupingIdentifier == 3) {
		if (cmd.nodeId.contains(zwaveHubNodeId)) {
			createEvent(name: "numberOfButtons", value: 2, displayed: false)
		}
		else {
			sendEvent(name: "numberOfButtons", value: 0, displayed: false)
				sendHubCommand(new physicalgraph.device.HubAction(zwave.associationV2.associationSet(groupingIdentifier: 3, nodeId: zwaveHubNodeId).format()))
				sendHubCommand(new physicalgraph.device.HubAction(zwave.associationV2.associationGet(groupingIdentifier: 3).format()))
		}
	}
}

def zwaveEvent(physicalgraph.zwave.commands.switchmultilevelv1.SwitchMultilevelReport cmd) {
	dimmerEvents(cmd)
}

def zwaveEvent(physicalgraph.zwave.commands.switchmultilevelv1.SwitchMultilevelSet cmd) {
	dimmerEvents(cmd)
}

private dimmerEvents(physicalgraph.zwave.Command cmd) {
	def value = (cmd.value ? "on" : "off")
	def result = [createEvent(name: "switch", value: value)]
	if (cmd.value && cmd.value <= 100) {
		result << createEvent(name: "level", value: cmd.value, unit: "%")
	}
	return result
}


def zwaveEvent(physicalgraph.zwave.commands.configurationv1.ConfigurationReport cmd) {
	def name = ""
	def value = ""
	def reportValue = cmd.configurationValue[0]
	log.debug "---CONFIGURATION REPORT V1--- ${device.displayName} parameter ${cmd.parameterNumber} with a byte size of ${cmd.size} is set to ${cmd.configurationValue}"
	switch (cmd.parameterNumber) {
		case 1:
			name = "topoff"
			value = reportValue == 1 ? "true" : "false"
			break
		case 2:
			switch (reportValue) {
				case 0:
					value = "off"
					break
				case 1:
					value = "on"
					break
				case 2:
					value = "never"
					break
				case 3:
					value = "always"
					break
				default:
					value = "off"
					break
			}
			name = "ledfollow"
			break
		case 3:
			name = "autooff"
			value = reportValue == 1 ? "true" : "false"
			break
		case 4:
			name = "autoofftimer"
            value = cmd.configurationValue[3] + (cmd.configurationValue[2] * 0x100) + (cmd.configurationValue[1] * 0x10000) + (cmd.configurationValue[0] * 0x1000000)
			break
		case 5:
			name = "autoon"
			value = reportValue == 1 ? "true" : "false"
			break
		case 6:
			name = "autoontimer"
            value = cmd.configurationValue[3] + (cmd.configurationValue[2] * 0x100) + (cmd.configurationValue[1] * 0x10000) + (cmd.configurationValue[0] * 0x1000000)
			break
		case 8:
			name = "afterfailure"
			switch (reportValue) {
				case 0:
					value = "off"
					break
				case 1:
					value = "on"
					break
				case 2:
					value = "remember"
					break
				default:
					break
			}
		case 9:
			name = "rampspeed"
			value = reportValue
			break
		case 10:
			name = "minbrightness"
			value = reportValue
			break
		case 11:
			name = "maxbrightness"
			value = reportValue
			break
		case 12:
			name = "double_tap"
			value = reportValue == 1 ? "true" : "false"
			break
		default:
			break
	}
	createEvent([name: name, value: value])
}

def zwaveEvent(physicalgraph.zwave.commands.hailv1.Hail cmd) {
	createEvent([name: "hail", value: "hail", descriptionText: "Switch button was pressed", displayed: false])
}

def zwaveEvent(physicalgraph.zwave.commands.manufacturerspecificv2.ManufacturerSpecificReport cmd) {
	def manufacturerCode = String.format("%04X", cmd.manufacturerId)
	def productTypeCode = String.format("%04X", cmd.productTypeId)
	def productCode = String.format("%04X", cmd.productId)
	def msr = manufacturerCode + "-" + productTypeCode + "-" + productCode
	updateDataValue("MSR", msr)
	updateDataValue("Manufacturer", "Zooz")
	updateDataValue("Manufacturer ID", manufacturerCode)
	updateDataValue("Product Type", productTypeCode)
	updateDataValue("Product Code", productCode)
	createEvent([descriptionText: "$device.displayName MSR: $msr", isStateChange: false])
}

def zwaveEvent(physicalgraph.zwave.commands.switchmultilevelv1.SwitchMultilevelStopLevelChange cmd) {
	[createEvent(name:"switch", value:"on"), response(zwave.switchMultilevelV1.switchMultilevelGet().format())]
}

def zwaveEvent(physicalgraph.zwave.commands.crc16encapv1.Crc16Encap cmd) {
	def versions = commandClassVersions
	def version = versions[cmd.commandClass as Integer]
	def ccObj = version ? zwave.commandClass(cmd.commandClass, version) : zwave.commandClass(cmd.commandClass)
	def encapsulatedCommand = ccObj?.command(cmd.command)?.parse(cmd.data)
	if (encapsulatedCommand) {
		zwaveEvent(encapsulatedCommand)
	}
}

def zwaveEvent(physicalgraph.zwave.Command cmd) {
	// Handles all Z-Wave commands we aren't interested in
	[:]
}

def on() {
	delayBetween([
			zwave.basicV1.basicSet(value: 0xFF).format(),
			zwave.switchMultilevelV1.switchMultilevelGet().format()
	],2000)
}

def off() {
	delayBetween([
			zwave.basicV1.basicSet(value: 0x00).format(),
			zwave.switchMultilevelV1.switchMultilevelGet().format()
	],2000)
}

def setLevel(value) {
	log.debug "setLevel >> value: $value"
	def valueaux = value as Integer
	def level = Math.max(Math.min(valueaux, 99), 0)
	if (level > 0) {
		sendEvent(name: "switch", value: "on")
	} else {
		sendEvent(name: "switch", value: "off")
	}
	sendEvent(name: "level", value: level, unit: "%")
	delayBetween ([zwave.basicV1.basicSet(value: level).format(), zwave.switchMultilevelV1.switchMultilevelGet().format()], 2000)
}

def setLevel(value, duration) {
	log.debug "setLevel >> value: $value, duration: $duration"
	def valueaux = value as Integer
	def level = Math.max(Math.min(valueaux, 99), 0)
	def dimmingDuration = duration < 128 ? duration : 128 + Math.round(duration / 60)
	def getStatusDelay = duration < 128 ? (duration*1000)+2000 : (Math.round(duration / 60)*60*1000)+2000
	delayBetween ([zwave.switchMultilevelV2.switchMultilevelSet(value: level, dimmingDuration: dimmingDuration).format(),
				zwave.switchMultilevelV1.switchMultilevelGet().format()], getStatusDelay)
}

def poll() {
	zwave.switchMultilevelV1.switchMultilevelGet().format()
}

/**
 * PING is used by Device-Watch in attempt to reach the Device
 * */
def ping() {
	refresh()
}

def refresh() {
	log.debug "refresh() is called"
	def commands = []
		if (getDataValue("MSR") == null) {
			commands << mfrGet()
			commands << zwave.versionV1.versionGet().format()
		}
	commands << zwave.switchMultilevelV1.switchMultilevelGet().format()
	delayBetween(commands,100)
}

def parmSet(parmnum, parmsize, parmval) {
	return zwave.configurationV1.configurationSet(scaledConfigurationValue: parmval, parameterNumber: parmnum, size: parmsize).format()
}

def parmGet(parmnum) {
	return zwave.configurationV1.configurationGet(parameterNumber: parmnum).format()
}

def mfrGet() {
	return zwave.manufacturerSpecificV2.manufacturerSpecificGet().format()
}

def zwaveEvent(physicalgraph.zwave.commands.versionv1.VersionReport cmd) {	
	updateDataValue("applicationVersion", "${cmd.applicationVersion}")
	updateDataValue("applicationSubVersion", "${cmd.applicationSubVersion}")
	updateDataValue("zWaveLibraryType", "${cmd.zWaveLibraryType}")
	updateDataValue("zWaveProtocolVersion", "${cmd.zWaveProtocolVersion}")
	updateDataValue("zWaveProtocolSubVersion", "${cmd.zWaveProtocolSubVersion}")
}

def zwaveEvent(physicalgraph.zwave.commands.versionv1.VersionCommandClassReport cmd) {
	log.debug "vccr"
	def rcc = ""
	log.debug "version: ${cmd.commandClassVersion}"
	log.debug "class: ${cmd.requestedCommandClass}"
	rcc = Integer.toHexString(cmd.requestedCommandClass.toInteger()).toString() 
	log.debug "${rcc}"
	if (cmd.commandClassVersion > 0) {log.debug "0x${rcc}_V${cmd.commandClassVersion}"}
}

private parseAssocGroupList(list, group) {
	def nodes = group == 2 ? [] : [zwaveHubNodeId]
 	if (list) {
		def nodeList = list.split(',')
		def max = group == 2 ? 5 : 4
		def count = 0

		nodeList.each { node ->
			node = node.trim()
			if ( count >= max) {
				log.warn "Association Group ${group}: Number of members is greater than ${max}! The following member was discarded: ${node}"
			}
			else if (node.matches("\\p{XDigit}+")) {
				def nodeId = Integer.parseInt(node,16)
				if (nodeId == zwaveHubNodeId) {
					log.warn "Association Group ${group}: Adding the hub as an association is not allowed (it would break double-tap)."
				}
				else if ( (nodeId > 0) & (nodeId < 256) ) {
					nodes << nodeId
					count++
				}
				else {
					log.warn "Association Group ${group}: Invalid member: ${node}"
				}
			}
			else {
				log.warn "Association Group ${group}: Invalid member: ${node}"
			}
		}
	}
 	return nodes
}