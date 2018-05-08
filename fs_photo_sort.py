""""This module starts GUI for file system photo sort program"""

# -*-coding:utf-8

from PIL import Image
import piexif
import glob
import sys

class ImageFileSorter:
    """Sort image files"""

    def __init__(self):
        self._raw_files = list()
        self._date_ordered_image_files = list()

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
                print(date)
        self._date_ordered_image_files = self._raw_files

if __name__ == '__main__':
    filesorter = ImageFileSorter()
    for arg in sys.argv[1:]:
        print (arg)
        filesorter.add_folder(arg)
    filesorter.filter_and_sort()
