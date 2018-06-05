""""This module starts GUI for file system photo sort program"""

# -*-coding:utf-8

from PIL import Image
from datetime import datetime
from pathlib import Path
from os.path import join
from shutil import copy
import piexif
import glob
import sys

class ImageFileSorter:
    """Sort image files"""

    def __init__(self, destination):
        self.destination = destination
        self._raw_files = list()

    def get_files(self):
        return self._raw_files

    def add_folder(self, folder):
        not_ordered_files = glob.glob(folder + "/*.jpg")
        self._raw_files = self._raw_files + not_ordered_files

    def filter_and_sort(self):
        for filename in self._raw_files:
            exif_dict = piexif.load(filename)
            date = exif_dict["Exif"].pop(piexif.ExifIFD.DateTimeOriginal)
            if date is not None:
                T=datetime.strptime('{}'.format(date),'b\'%Y:%m:%d %H:%M:%S\'')
                year = T.year
                month = T.month
                destPath = Path(self.destination)
                destPathYear = Path(join(destPath, str(year)))
                destPathMonth = Path(join(destPathYear, str(month)))
                if not destPathYear.exists():
                    destPathYear.mkdir()
                    print("making " + str(destPathYear))
                if not destPathMonth.exists():
                    destPathMonth.mkdir()
                    print("making " + str(destPathMonth))
                if destPathMonth.exists():
                    print("copying " + str(filename) + " to " + str(destPathMonth))
                    copy(filename, destPathMonth)
        print("Photo sort finished")

if __name__ == '__main__':
    destination = sys.argv[1]
    filesorter = ImageFileSorter(destination)
    for arg in sys.argv[2:]:
        print (arg)
        filesorter.add_folder(arg)
    filesorter.filter_and_sort()
