#!/usr/bin/python

# Builds a release zip folder

import sys
import os
import re
from subprocess import call
import zipfile
import xml.etree.ElementTree as ET

PROJECT_NAME = "EwonConnector"
IGNITION_VERSION = "8"

#Path that the release file gets saved to
RELEASE_PATH = "../releases/"

#File names for release files
README_FILENAME          = "README.md"
CHANGELOG_FILENAME       = "CHANGELOG.md"
MODL_FILENAME            = PROJECT_NAME + "-Ignition-Module-signed.modl"
MODL_FILENAME_UNSIGNED   = PROJECT_NAME + "-Ignition-Module-unsigned.modl"
BUILD_XML_FILENAME       = "pom.xml"
RELEASE_FOLDER_EXT       = ".zip"
UNSIGNED_RELEASE_POSTFIX = "-unsigned"


#Paths for release files
README_PATH     = "../"
CHANGELOG_PATH  = "../"
MODL_PATH       = "../eWonConnector-build/target"
BUILD_XML_PATH  = "../eWonConnector-build"

# Returns the namespace of the xml element
def GetNamespace(element):
   match = re.match('\{.*\}', element.tag)
   res = ''
   if match:
      FIRST_MATCH_INDEX = 0
      res = match.group(FIRST_MATCH_INDEX)
   return res

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

   isReleaseBuildSuccess = True
   isSignedRelease = True

   moduleVersion = GetModuleVersion()

   #Create releases directory if it does not already exist
   if not os.path.exists(RELEASE_PATH):
      os.makedirs(RELEASE_PATH)

   releaseFilename = RELEASE_PATH + PROJECT_NAME + "-" + IGNITION_VERSION + "-" + moduleVersion

   #Create the release zip folder
   zf = zipfile.ZipFile(("%s" + RELEASE_FOLDER_EXT) % releaseFilename, "w", zipfile.ZIP_DEFLATED)

   #Add all "release" files to the zip
   zf.write(os.path.abspath(os.path.join(README_PATH,README_FILENAME)), README_FILENAME)
   zf.write(os.path.abspath(os.path.join(CHANGELOG_PATH,CHANGELOG_FILENAME)), CHANGELOG_FILENAME)

   try:
      zf.write(os.path.abspath(os.path.join(MODL_PATH,MODL_FILENAME)), MODL_FILENAME)
   except OSError:
      try:
         zf.write(os.path.abspath(os.path.join(MODL_PATH,MODL_FILENAME_UNSIGNED)), MODL_FILENAME_UNSIGNED)
         isSignedRelease = False
      except OSError:
         print "MODL file does not exist"
         isReleaseBuildSuccess = False

   #Close the release zip folder
   zf.close()

   if isReleaseBuildSuccess:
      if isSignedRelease:
         print "Successfully made release: " + releaseFilename + RELEASE_FOLDER_EXT
      else:
         os.rename(releaseFilename+".zip", releaseFilename + UNSIGNED_RELEASE_POSTFIX + RELEASE_FOLDER_EXT)
         print "MODL file is unsigned"
         print "Successfully made release: " + releaseFilename + UNSIGNED_RELEASE_POSTFIX + RELEASE_FOLDER_EXT
   else:
      print "Could not build release"
      os.remove(releaseFilename + RELEASE_FOLDER_EXT)

if __name__ == '__main__':

   if len(sys.argv) != 1:
      print "Usage: MakeRelease.py"
      sys.exit(0)

   CreateRelease()