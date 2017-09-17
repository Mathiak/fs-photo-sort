""""This module starts GUI for file system photo sort program"""

# -*-coding:utf-8

from tkinter import *
from tkinter.filedialog import *
from PIL import Image
import piexif
import glob

class PhotoSortFrame(Frame):
    """Class defining photo sort main frame"""

    def __init__(self, myframe, filesorter, **kwargs):
        Frame.__init__(self, myframe, width=800, height=600, **kwargs)
        self.parent = myframe
        self.__filesorter = filesorter

        # frame 1
        frame1 = LabelFrame(
            myframe,
            text="Source folders containing photos to sort",
            padx=20,
            pady=20)
        frame1.pack(fill="both", expand="yes")
        # Ajout de labels
        add_source_button = Button(
            frame1, text="Add Folder Location", command=self.add_source_folder)
        add_source_button.pack()
        self.pack(fill=BOTH)

    def add_source_folder(self):
        filepath = askdirectory()
        self.__filesorter.add_folder(filepath)
        self.__filesorter.filter_and_sort()

class ImageFileSorter:
    """Sort image files"""

    def __init__(self):
        self._raw_files = list()
        self._date_ordered_image_files = list()

    def get_files(self):
        return self._raw_files

    def add_folder(self, folder):
        not_ordered_files = glob.glob(folder + "*.jpg")
        self._raw_files = self._raw_files + not_ordered_files

    def filter_and_sort(self):
        print( self._raw_files)
        for filename in self._raw_files:
            exif_dict = piexif.load(filename)
            date = exif_dict.pop("DateTime")
            if date is not None:
                print(date)
        self._date_ordered_image_files = self._raw_files

if __name__ == '__main__':
    FRAME = Tk()
    FRAME.title("FS Photot Sort")
    FILE_SORTER = ImageFileSorter()
    PHOTOFRAME = PhotoSortFrame(FRAME, FILE_SORTER)
    PHOTOFRAME.mainloop()
