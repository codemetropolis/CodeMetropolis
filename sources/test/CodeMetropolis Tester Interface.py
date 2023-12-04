import tkinter as tk
from tkinter import *
from PIL import Image, ImageTk
import subprocess
import os
import io
from tkinter import StringVar
from tkinter import filedialog
from tkinter import ttk

entries = []
entries_converter = []
entries_mapping = []
config_dict = {}
lang_dict = {}
program_name = "CodeMetropolis Tester Interface"
program_name_full = "CodeMetropolis Tester Interface.py"
program_version = " v1.2"
tk_window_size = "1000x600"
tester_file_name = "\"CodeMetropolis Tester.py\""
def_font = ("Helvetica", 10)
page_font = ("Helvetica", 12, "bold")
program_icon_path = 'config/icon.ico'
program_logo_path = "config/logo_transparent.png"
last_textbox_inputs_file_path = "config/textbox_last_input.txt"
config_file_path = "config/config.txt"
eng_lang_file_path = "config/english_language_file.txt"
hun_lang_file_path = "config/hungarian_language_file.txt"
default_config_lines = "language=eng\ntheme=light"

def defaultConfigWriter():
    c = open(config_file_path, "w+")
    c.write(default_config_lines)
    c.close()

#load file functon
def loadTxtFile(file_path):
    lines_dict = {}
    with io.open(file_path, mode="r", encoding="utf-8") as f:
        lines = f.readlines()

    for line in lines:
        line_parts = line.split("=")
        lines_dict[line_parts[0]] = line_parts[1].strip()
    return lines_dict

config_dict = loadTxtFile(config_file_path)

#theme set
if config_dict["theme"] == "dark":
    BG_COLOR = "#121212"
    FONT_COLOR = "#FFFFFF"
    BTN_COLOR = "#BB86FC"
else:
    BG_COLOR = "#DEE4E7"
    FONT_COLOR = "#000000"
    BTN_COLOR = "#FFFFFF"

lang_dict = loadTxtFile(eng_lang_file_path)
if config_dict["language"] == "hun":
    lang_dict = loadTxtFile(hun_lang_file_path)

ABOUT_TXT = lang_dict["Desc"] + "\n\n" + lang_dict["MadeBy"] + "\n" + lang_dict["MadeYear"]
HOW_TO_TXT = lang_dict["Step1"] + "\n\n" + lang_dict["Step2"] + "\n\n" + lang_dict["Step3"] + "\n\n" + lang_dict["Step4"] + "\n\n" + lang_dict["Step5"] + "\n\n" + lang_dict["Step6"] + "\n\n" + lang_dict["Step7"] + "\n\n" + lang_dict["Step8"] + "\n\n" + lang_dict["Step9"] 

#choose file button function
def selectFile(entry):
    file_path = filedialog.askopenfilename()
    entry.delete(0, tk.END)
    entry.insert(0, file_path)

#choose folder button function
def selectFolder(entry):
    folder_path = filedialog.askdirectory()
    entry.delete(0, tk.END)
    entry.insert(0, folder_path)

#refresh function
def refreshApp(self):
    self.destroy()
    os.startfile(program_name_full)

#the interface is separated from the testing execution so all arguments will be given to a tester file
def CmdCommand():  
    arg1 = entries[0].get()
    arg2 = entries[1].get()
    arg3 = entries[2].get()
    arg4 = entries[3].get()

    cmd = f"{tester_file_name} {arg1} {arg2} {arg3} {arg4}"

    #if selected tool converter, you can add type and parameters values
    if len(entries_converter) == 2:
        arg5 = "--type " + entries_converter[0].get()
        if not entries_converter[1].get().strip():
            arg6 = "--parameters " + "None"
        else:
            arg6 = "--parameters " + entries_converter[1].get()

        cmd += f" {arg5} {arg6}"

    #if selected tool mapping, you need to add Metrics assign file value
    if len(entries_mapping) == 1:
        arg5 = "--mapping_file_path " + entries_mapping[0].get()
        cmd += f" {arg5}"

    subprocess.call(cmd, shell=True)
    print(cmd)


class App(tk.Tk):
    def __init__(self):
        super().__init__()
        self.geometry(tk_window_size)
        self.title( program_name + program_version)
        self.resizable(width=False, height=False)
        self.iconbitmap(program_icon_path)
        image = Image.open(program_logo_path)
        image = image.resize((150, 150))
        logo = ImageTk.PhotoImage(image)
    
        gridNSEW = {"padx": 10, "pady": 10, "sticky": "nsew"}
        gridEW = {"padx": 10, "pady": 10, "sticky": "ew"}
        color_font = {"bg": BG_COLOR, "fg": FONT_COLOR,  "font": def_font}
        button_font = {"bg": BTN_COLOR, "fg": FONT_COLOR,  "font": def_font}

        def configWriter():
            c = open(config_file_path, "w+")
            c.write(list(config_dict.keys())[0] + "=" + config_dict["language"] + "\n" + list(config_dict.keys())[1] + "=" + config_dict["theme"] + "\n")
            c.close()
            refreshApp(self)

        def changeTheme(selected_mode):
            if (selected_mode == lang_dict["Dark"]):
                config_dict["theme"] = "dark"
            else: 
                config_dict["theme"] = "light"
            configWriter()

        def changeLanguage(selected_language):
            match selected_language:
                case str(selected_language) if lang_dict["Magyar"] in selected_language: 
                    config_dict["language"] = "hun"
                case _:
                    config_dict["language"] = "eng"
            configWriter()
        
        #MENU
        menubar = tk.Menu(self)
        #Tool menu
        file_menu = tk.Menu(menubar, tearoff=0)

        file_menu.add_command(label=lang_dict["Converter"], command=lambda: (changeMainPage(lang_dict["ConverterTool"])))
        file_menu.add_command(label=lang_dict["Mapping"], command=lambda: (changeMainPage(lang_dict["MappingTool"])))
        file_menu.add_command(label=lang_dict["Placing"], command=lambda: (changeMainPage(lang_dict["PlacingTool"])))
        file_menu.add_command(label=lang_dict["Rendering"], command=lambda: (changeMainPage(lang_dict["RenderingTool"])))
        menubar.add_cascade(label=lang_dict["ToolMenu"], menu=file_menu)

        #Language menu
        language_menu = tk.Menu(menubar, tearoff=0)
        language_menu.add_command(label=lang_dict["English"], command=lambda: (changeLanguage(lang_dict["English"])))
        language_menu.add_command(label=lang_dict["Magyar"], command=lambda: (changeLanguage(lang_dict["Magyar"])))
        menubar.add_cascade(label=lang_dict["Language"], menu=language_menu)

        #theme menu
        theme_menu = tk.Menu(menubar, tearoff=0)
        theme_menu.add_command(label=lang_dict["Dark"], command=lambda: (changeTheme(lang_dict["Dark"])))
        theme_menu.add_command(label=lang_dict["Light"], command=lambda: (changeTheme(lang_dict["Light"])))
        menubar.add_cascade(label=lang_dict["Theme"], menu=theme_menu)        

        #Help menu
        help_menu = tk.Menu(menubar, tearoff=0)
        help_menu.add_command(label=lang_dict["HowMenu"], command=lambda: changeMainPage(lang_dict["HowMenu"]))
        help_menu.add_command(label=lang_dict["AboutMenu"], command=lambda: changeMainPage(lang_dict["AboutMenu"]))
        menubar.add_cascade(label=lang_dict["HelpMenu"], menu=help_menu)

        #display the menubar
        self.config(menu=menubar, bg=BG_COLOR)

        #PAGES
        def labelCreate(name):
            return tk.Label(self, text=name, **color_font)
        
        def entryCreate(entry_name):
            return tk.Entry(self, name=entry_name, **button_font)
        
        def fileButtonCreate(button_text,entry):
            return tk.Button(self, text=button_text, command=lambda e=entry: selectFile(e), **button_font)
        
        def folderButtonCreate(button_text,entry):
            return tk.Button(self, text=button_text, command=lambda e=entry: selectFolder(e), **button_font)
        
        #every tool contain Test file/folder, Input file/folder, Expected file/folder, Output labels, textboxes and buttons
        def commonGuiElements():
            arg_labels = [lang_dict["TestFile"], lang_dict["InputFile"], lang_dict["ExpectedFile"], lang_dict["OutputFolder"]]
            row_index = 1

            for i, label_text in enumerate(arg_labels):
                label = labelCreate(label_text)
                label.grid(row=row_index+i, column=0, **gridNSEW)

                entry = entryCreate("entry" + str(i))
                entry.grid(row=row_index+i, column=1, **gridEW)
                entries.append(entry)
                
                if (i < 3):
                    button = fileButtonCreate(lang_dict["SelectFile"],entry)
                    button.grid(row=row_index+i, column=2, **gridNSEW)

                folder_button = folderButtonCreate(lang_dict["SelectFolder"],entry)
                if (i == 3):
                    folder_button.grid(row=row_index+i, column=2, **gridNSEW)
                else:
                    folder_button.grid(row=row_index+i, column=3, **gridNSEW)
        
        #converter tool need Type and Parameters
        def converterGuiPlusElements():
            converter_cb_options = [lang_dict["ConvSourceMeter"],lang_dict["ConvPmd"],lang_dict["ConvGitlab"],lang_dict["ConvGitstats"]]

            label = labelCreate(lang_dict["Type"])
            label.grid(row=5, column=0, **gridNSEW)

            self.option_add('*TCombobox*Listbox.background', BTN_COLOR)
            self.option_add('*TCombobox*Listbox.foreground', FONT_COLOR)
            entry = ttk.Combobox(self, values=converter_cb_options, state='readonly', name="entry4")
            
            entry.grid(row=5, column=1, **gridEW)
            entries_converter.append(entry)
            
            label = labelCreate(lang_dict["OptParam"])
            label.grid(row=6, column=0, **gridNSEW)

            entry = entryCreate("entry5")
            entry.grid(row=6, column=1, **gridEW) 

            #put textboxes values to entries_converter array
            entries_converter.append(entry)

        #mapping tool need Metrics assign file
        def mappingGuiPlusElements():
            label = labelCreate(lang_dict["Metrics"])
            label.grid(row=5, column=0, **gridNSEW)

            entry = entryCreate("entry4")
            entry.grid(row=5, column=1, **gridEW) 

            entries_mapping.append(entry)

            button = fileButtonCreate(lang_dict["SelectFile"],entry)
            button.grid(row=5, column=2, **gridNSEW)

        #about page
        def aboutPage():
            label = tk.Label(self, text= program_name + program_version, font=("Helvetica", 20, "bold"), bg=BG_COLOR, fg = FONT_COLOR)
            label.grid(row=0, column=2, **gridNSEW)

            label = tk.Label(self, image=logo, **color_font)
            label.grid(row=1, column=2)

            label = tk.Label(self, text=ABOUT_TXT, font=def_font, bg=BG_COLOR,  fg = FONT_COLOR)
            label.grid(row=2, column=2, **gridNSEW)
            
        #How to? page
        def howToPage():
            label = tk.Label(self, text= lang_dict["HowUse"] + program_name, font=("Helvetica", 20), bg=BG_COLOR, fg = FONT_COLOR)
            label.grid(row=0, column=2, **gridNSEW)

            label = tk.Label(self, text=HOW_TO_TXT, font=def_font, justify="left", anchor="w", bg=BG_COLOR,  fg = FONT_COLOR)
            label.grid(row=1, column=2)

        #run button
        def runButton(page_name):
            run_button = tk.Button(self, text=lang_dict["RunBtn"], command=lambda: (CmdCommand(), lastInputsSave(page_name), changeMainPage(page_name)), **button_font)
            run_button.grid(row=7, column=1, **gridNSEW)

        #about page be the default page
        aboutPage()   

        #Load your last used textbox inputs from a file
        #load button
        def loadButton(page_name):
            if (os.path.isfile(last_textbox_inputs_file_path)):
                run_button = tk.Button(self, text=lang_dict["LoadInputs"], command=lambda: (lastInputsLoad(page_name)), **button_font)
                run_button.grid(row=7, column=2, **gridNSEW)

        #save textbox content to txt
        def lastInputsSave(page_name):
            with open(last_textbox_inputs_file_path, 'w') as f:
                for i in range (4):
                    f.write(entries[i].get() + "\n")

                #if tool is converter, save type and optional parameter textbox content
                match page_name:
                    case str(page_name) if lang_dict["ConverterTool"] in page_name:
                        f.write(entries_converter[0].get() + "\n")
                        f.write(entries_converter[1].get() + "\n")
                    case str(page_name) if lang_dict["MappingTool"] in page_name:
                        f.write(entries_mapping[0].get() + "\n")

            #clear all textbox
            entries.clear()
            entries_converter.clear()
            entries_mapping.clear()

            loadButton(page_name)

        #load txt file lines to textboxes
        def lastInputsLoad(page_name):
            with open(last_textbox_inputs_file_path, 'r') as f:
                lines = f.readlines()

            for i, line in enumerate(lines):
                entry_name = "entry" + str(i)

                if entry_name in self.children:
                    entry = self.nametowidget(entry_name)
                    entry.delete(0, tk.END)
                    entry.insert(0, line.strip())

                    if entry_name == "entry4" and page_name == lang_dict["ConverterTool"]:
                        entry = self.nametowidget(entry_name)
                        newLine = line.strip()
                        match newLine:
                            case str(newLine) if lang_dict["ConvSourceMeter"] in newLine:
                                entry.current(0)
                            case str(newLine) if lang_dict["ConvPmd"] in newLine:
                                entry.current(1)
                            case str(newLine) if lang_dict["ConvGitlab"] in newLine:
                                entry.current(2)
                            case str(newLine) if lang_dict["ConvGitstats"] in newLine:
                                entry.current(3)

        #row and column alignment
        for i in range(5):
            self.grid_columnconfigure(i, weight=1)
        for i in range(8):
            self.grid_rowconfigure(i, weight=1)

        #main page switch function
        def switchMenuCommon(page_name,name):
            commonGuiElements()
            runButton(page_name)   
            loadButton(page_name)
            entries_mapping.clear()
            label = tk.Label(self, text=name, bg=BG_COLOR, font=page_font , fg = FONT_COLOR)
            label.grid(row=0, column=1, **gridNSEW)
        
        def changeMainPage(page_name):
            for widget in self.grid_slaves():
                widget.grid_forget()

            match page_name:
                case str(page_name) if lang_dict["ConverterTool"] in page_name:
                    switchMenuCommon(page_name,lang_dict["ConverterTool"])
                    converterGuiPlusElements()
                    
                case str(page_name) if lang_dict["MappingTool"] in page_name:
                    switchMenuCommon(page_name,lang_dict["MappingTool"])
                    mappingGuiPlusElements()

                case str(page_name) if lang_dict["PlacingTool"] in page_name:
                    switchMenuCommon(page_name,lang_dict["PlacingTool"])

                case str(page_name) if lang_dict["RenderingTool"] in page_name:
                    switchMenuCommon(page_name,lang_dict["RenderingTool"])

                case str(page_name) if lang_dict["HelpMenu"] in page_name :
                    aboutPage()

                case str(page_name) if lang_dict["HowMenu"] in page_name :
                    howToPage()

                case _:
                    aboutPage()          

if __name__ == "__main__":
  app = App()
  app.mainloop()
