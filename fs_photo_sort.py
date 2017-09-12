""""This module starts GUI for file system photo sort program"""

# -*-coding:utf-8

from tkinter import *
from tkinter.filedialog import *

class PhotoSortFrame(Frame):
    """Class defining photo sort main frame"""

    def __init__(self, myframe, **kwargs):
        Frame.__init__(self, myframe, width=800, height=600, **kwargs)
        # frame 1
        frame1 = LabelFrame(myframe, text="Source folders containing photos to sort", padx=20, pady=20)
        frame1.pack(fill="both", expand="yes")
        # Ajout de labels
        add_source_button=Button(frame1, text="Add Folder Location", command=self.add_source_folder)
        add_source_button.pack()
        self.pack(fill=BOTH)

    def add_source_folder(self):
        filepath = askopenfilename(title="Ouvrir une image",filetypes=[('png files','.png'),('all files','.*')])


if __name__ == '__main__':
    FRAME = Tk()
    FRAME.title("FS Photot Sort")
    PHOTOFRAME = PhotoSortFrame(FRAME)

    PHOTOFRAME.mainloop()
