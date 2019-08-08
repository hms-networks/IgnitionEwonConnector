#!/usr/bin/python

# Builds a release zip folder

import sys
import os
import re
from subprocess import call
import zipfile
import xml.etree.ElementTree as ET

PROJECT_NAME = "EwonConnector"
IGNITION_VERSION = "7_9"

#Path that the release file gets saved to
RELEASE_PATH = "../releases/"

#File names for release files
README_FILENAME     = "README.md"
CHANGELOG_FILENAME  = "CHANGELOG.md"
MODL_FILENAME       = PROJECT_NAME + "-Ignition-Module-signed.modl"
BUILD_XML_FILENAME  = "pom.xml"


#Paths for release files
README_PATH     = "../"
CHANGELOG_PATH  = "../"
MODL_PATH       = "../eWonConnector-build/target"
BUILD_XML_PATH  = "../eWonConnector-build"

# Returns the namespace of the xml element
def GetNamespace(element):
   match = re.match('\{.*\}', element.tag)
   return match.group(0) if match else ''

# Returns the module version based on the build xml file
def GetModuleVersion():
   # create element tree object
   tree = ET.parse(os.path.join(BUILD_XML_PATH,BUILD_XML_FILENAME))

   # get root element
   root = tree.getroot()

   # get the namespace of the xml doc
   namespace = GetNamespace(root)

   # find the module version in the tree
   moduleVersion = root.find(".//" + namespace + "moduleVersion").text
   return moduleVersion

def CreateRelease():

   moduleVersion = GetModuleVersion()

   #Create releases directory if it does not already exist
   if not os.path.exists(RELEASE_PATH):
      os.makedirs(RELEASE_PATH)

   releaseFilename = RELEASE_PATH + PROJECT_NAME + "-" + IGNITION_VERSION + "-" + moduleVersion

   #Create the release zip folder
   zf = zipfile.ZipFile("%s.zip" % releaseFilename, "w", zipfile.ZIP_DEFLATED)

   #Add all "release" files to the zip
   zf.write(os.path.abspath(os.path.join(README_PATH,README_FILENAME)), README_FILENAME)
   zf.write(os.path.abspath(os.path.join(CHANGELOG_PATH,CHANGELOG_FILENAME)), CHANGELOG_FILENAME)
   zf.write(os.path.abspath(os.path.join(MODL_PATH,MODL_FILENAME)), MODL_FILENAME)

   #Close the release zip folder
   zf.close()

   print "Successfully made release: " + releaseFilename + ".zip"

if __name__ == '__main__':

   if len(sys.argv) != 1:
      print "Usage: MakeRelease.py"
      sys.exit(0)

   CreateRelease()
